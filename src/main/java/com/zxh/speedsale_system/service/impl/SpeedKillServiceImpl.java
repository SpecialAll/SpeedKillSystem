package com.zxh.speedsale_system.service.impl;

import com.alibaba.druid.util.ListDG;
import com.zxh.speedsale_system.dto.Exposer;
import com.zxh.speedsale_system.dto.SpeedKillExecution;
import com.zxh.speedsale_system.entity.GoodsDO;
import com.zxh.speedsale_system.entity.GoodsVO;
import com.zxh.speedsale_system.entity.KillOrderVO;
import com.zxh.speedsale_system.entity.OrderDetailDO;
import com.zxh.speedsale_system.enums.SpeedKillStateEnum;
import com.zxh.speedsale_system.exception.RepeatKillException;
import com.zxh.speedsale_system.exception.SpeedKillCloseException;
import com.zxh.speedsale_system.exception.SpeedKillException;
import com.zxh.speedsale_system.mapper.SpeedKillGoodsMapper;
import com.zxh.speedsale_system.mapper.SpeedKillOrderMapper;
import com.zxh.speedsale_system.service.SpeedKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 *
 * 秒杀服务实现类
 */
@Service
@Slf4j
public class SpeedKillServiceImpl implements SpeedKillService {
    //设置秒杀redis缓存的key
    private static final String key = "goods";
    //设置盐值字符串（任意设置）
    public static final String salt = "daovnauhuaenaiaoei&%#dsv897d7dae";

    @Autowired
    private SpeedKillGoodsMapper goodsMapper;

    @Autowired
    private SpeedKillOrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 查看所有秒杀商品的信息
     *
     * 在查询环节使用redis缓存来解决提示效率问题
     */
    @Override
    public List<GoodsVO> findAll() {
        List<GoodsDO> list = redisTemplate.boundHashOps("goods").values();
        if(list.isEmpty()){
            //如果进入判断，则说明缓存中没有秒杀商品列表信息
            //然后去查询数据库
            list = goodsMapper.findAll();
            for(GoodsDO goods : list){
                //将秒杀数据依次放入到redis缓存中去
                redisTemplate.boundHashOps(key).put(goods.getGoodsId(),goods);
                log.info("findAll ——> 从数据中读取数据放入redis缓存中");
            }
        } else {
            log.info("findAll ——> 从缓存中读取数据");
        }
        List<GoodsVO> ansList = new ArrayList<>();
        for(GoodsDO goods : list){
            GoodsVO goodsVO = getGoodsVo(goods);
            ansList.add(goodsVO);
        }
        return ansList;
    }

    @Override
    public GoodsVO findById(long id) {
        return getGoodsVo(goodsMapper.findById(id));
    }

    /**
     * 暴露秒杀地址URL（用于防止重复刷单操作，在刷单时会将当前商品秒杀地址和暴露出去的地址进行对比判断）
     * @param killGoodsId
     * @return
     */
    @Override
    public Exposer exportSpeedKillUrl(long killGoodsId) {
        GoodsDO goods = (GoodsDO) redisTemplate.boundHashOps(key).get(killGoodsId);
        if(goods == null){
            //为空则表示缓存中没有秒杀信息，去数据库中查询
            goods = goodsMapper.findById(killGoodsId);
            if(goods == null){
                //表示没有此类商品
                return new Exposer(false,killGoodsId);
            } else {
                //将查询到的商品信息存储到缓存中
                redisTemplate.boundHashOps(key).put(killGoodsId, goods);
                log.info("RedisTemplate ——> 从数据库中读取数据存入缓存");
            }
        } else {
            log.info("RedisTemplate ——> 缓存获取数据");
        }
        Date startTime = goods.getStartTime();
        Date endTime = goods.getEndTime();
        //获取系统时间
        Date nowTime = new Date();
        if(startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()){
            return new Exposer(false, killGoodsId, nowTime.getTime(), startTime.getTime(),endTime.getTime());
        }
        /**
         * /使用MD5 + 盐的加密方式
         * 我们要做到接口防刷的功能，所以需要生成一串md5值作为秒杀接口中一部分。
         * 而Spring提供了一个工具类DigestUtils用于生成MD5值，
         * 且又由于要做到更安全所以我们采用md5+盐的加密方式生成一传md5加密数据作为秒杀URL地址的一部分发送给Controller。
         */
        String md5 = getMD5(killGoodsId);
        return new Exposer(true,md5,killGoodsId);
    }

    /**
     * 执行秒杀的业务方法
     * 秒杀逻辑包含：
     *      1、减库存
     *      2、存储秒杀订单
     *   所以需要使用到事务来解决超卖等可能存在的异常问题
     *
     *   Spring默认只对运行期异常（RuntimeException）进行事务回滚操作，对于编译异常Spring是不进行回滚的，
     *   所以对于需要进行事务控制的方法尽量将可能抛出的异常都转换成运行期异常。
     *   这也是我们我什么要在Service接口中手动封装一些RuntimeException信息的一个重要原因。
     *
     *
     * @param goodsId
     * @param money
     * @param userId
     * @param md5
     * @return
     * @throws SpeedKillException
     * @throws SpeedKillCloseException
     * @throws RepeatKillException
     */
    @Override
    @Transactional
    public SpeedKillExecution executeSpeedKill(long goodsId, BigDecimal money, long userId, String md5) throws SpeedKillException, SpeedKillCloseException, RepeatKillException {
        //用传入的MD5值和当前秒杀地址比较，是否为同一个秒杀
        if(md5 == null || !md5.equals(getMD5(goodsId))){
            throw new SpeedKillException("speedKill data reWrite");
        }
        /**
         * 先存储秒杀订单信息，然后再减库存
         */
        Date nowTime = new Date();
        try {
            //存储订单信息
            int insertCount = orderMapper.insertOrder(goodsId,money,userId);
            if(insertCount <= 0){
                throw new RepeatKillException("speedKill Repeat");
            } else {
                //减库存操作
                int updateCount = goodsMapper.reduceStock(goodsId,nowTime);
                if(updateCount <= 0){
                    //没有更新库存，代表秒杀结束
                    throw new SpeedKillException("speedKill is closed");
                } else {
                    //秒杀成功
                    OrderDetailDO orderDO = orderMapper.findByUserIdAndGoodsId(userId, goodsId);

                    //更新缓存更新缓存数量
                    GoodsDO goods = (GoodsDO) redisTemplate.boundHashOps(key).get(goodsId);
                    goods.setStockCount(goods.getStockCount() - 1);
                    redisTemplate.boundHashOps(key).put(goodsId, goods);

                    return new SpeedKillExecution(goodsId, SpeedKillStateEnum.SUCCESS, getOrderVO(orderDO));
                }
            }
        } catch (SpeedKillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            //所有编译期异常，更改为运行时异常
            throw new SpeedKillException("skill inner error" + e.getMessage());
        }
    }


    private GoodsVO getGoodsVo(GoodsDO goods) {
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoodsId(goods.getGoodsId());
        goodsVO.setEndTime(goods.getEndTime());
        goodsVO.setGoodsTitle(goods.getGoodsTitle());
        goodsVO.setImage(goods.getImage());
        goodsVO.setKillPrice(goods.getKillPrice());
        goodsVO.setStartTime(goods.getStartTime());
        goodsVO.setStockCount(goods.getStockCount());
        goodsVO.setPrice(goods.getPrice());

        return goodsVO;
    }


    private String getMD5(long killGoodsId) {
        String base = killGoodsId + "/" +salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    private KillOrderVO getOrderVO(OrderDetailDO orderDO) {
        KillOrderVO orderVO = new KillOrderVO();
        orderVO.setCreateTime(orderDO.getCreateTime());
        orderVO.setGoodsId(orderDO.getGoodsId());
        orderVO.setMoney(orderDO.getMoney());
        orderVO.setStatus(orderDO.getStatus());
        orderVO.setUserId(orderDO.getUserId());

        return orderVO;
    }

}
