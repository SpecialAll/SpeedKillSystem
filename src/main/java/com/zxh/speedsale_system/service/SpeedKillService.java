package com.zxh.speedsale_system.service;

import com.zxh.speedsale_system.dto.Exposer;
import com.zxh.speedsale_system.dto.SpeedKillExecution;
import com.zxh.speedsale_system.entity.GoodsVO;
import com.zxh.speedsale_system.exception.RepeatKillException;
import com.zxh.speedsale_system.exception.SpeedKillCloseException;
import com.zxh.speedsale_system.exception.SpeedKillException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 *
 * 秒杀业务接口
 */
public interface SpeedKillService {
    /**
     * 获取所有的秒杀商品列表
     */
    List<GoodsVO> findAll();

    /**
     * 获取某一条商品的秒杀信息
     */
    GoodsVO findById(long id);

    /**
     * 秒杀开始时暴露秒杀地址
     * 否则输出当前时间和秒杀时间
     */
    Exposer exportSpeedKillUrl(long killGoodsId);

    /**
     * 执行秒杀操作
     */
    SpeedKillExecution executeSpeedKill(long goodsId, BigDecimal money, long userId, String md5)
        throws SpeedKillException, SpeedKillCloseException, RepeatKillException;
}
