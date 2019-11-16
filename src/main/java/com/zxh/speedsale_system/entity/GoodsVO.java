package com.zxh.speedsale_system.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 *
 *  商品信息实体类 - Vo service层返回值
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsVO {
    private long goodsId;  //商品id
    private String goodsTitle;  //商品标题
    private String image;  //商品图片
    private BigDecimal price;  //商品原价格
    private BigDecimal killPrice; //秒杀价格；

    private Date startTime;  //秒杀开始时间

    private Date endTime;  //秒杀结束时间

    private  long stockCount; //库存数量
}
