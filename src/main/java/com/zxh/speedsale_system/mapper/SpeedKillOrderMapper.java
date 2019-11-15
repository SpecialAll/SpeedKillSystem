package com.zxh.speedsale_system.mapper;

import com.zxh.speedsale_system.entity.OrderDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 *
 * 订单-数据库操作
 */
@Mapper
public interface SpeedKillOrderMapper {
    /**
     * 插入秒杀订单
     * @param goodsId 商品id
     * @param money   支付金额
     * @param userId  用户id
     * @return  返回该sql更新的记录数，如果>=1,表示插入成功
     */
    int insertOrder(@Param("goodsId") long goodsId, @Param("money") BigDecimal money, @Param("userId") long userId);

    /**
     * 用户查看自己的所有秒杀商品订单
     *
     * @param userId
     * @return
     */
    List<OrderDetailDO> finbByUserId(@Param("userId") long userId);
}
