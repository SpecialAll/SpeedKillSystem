package com.zxh.speedsale_system.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 *
 * 订单表实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDO {
    private long orderId;  //订单id
    private long userId;   //用户id
    private long goodsId;   //秒杀的商品id
    private BigDecimal money;  //支付的金额；
    private boolean status; //订单状态， -1:无效 0:成功 1:已付款
    private Date createTime;  //创建时间
}
