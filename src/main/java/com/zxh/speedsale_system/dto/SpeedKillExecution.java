package com.zxh.speedsale_system.dto;

import com.zxh.speedsale_system.entity.KillOrderVO;
import com.zxh.speedsale_system.entity.OrderDetailDO;
import com.zxh.speedsale_system.enums.SpeedKillStateEnum;
import com.zxh.speedsale_system.exception.SpeedKillException;
import lombok.*;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 *
 * 秒杀结果返回值
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpeedKillExecution {
    //秒杀商品id
    private long goodsId;

    //秒杀执行结果的状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功的订单对象
    private KillOrderVO killOrder;

    public SpeedKillExecution(long goodsId, SpeedKillStateEnum stateEnum, KillOrderVO orderVO){
        this.goodsId = goodsId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.killOrder = orderVO;
    }

    public SpeedKillExecution(long goodsId, SpeedKillStateEnum stateEnum){
        this.goodsId = goodsId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}
