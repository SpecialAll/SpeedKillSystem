package com.zxh.speedsale_system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 *
 * (秒杀订单实体类）
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KillOrderVO {
    private long goodsId;
    private BigDecimal money;
    private long userId;
    private Date createTime;
    private boolean status;  //订单状态：-1 无效，0成功，1 已付款

}
