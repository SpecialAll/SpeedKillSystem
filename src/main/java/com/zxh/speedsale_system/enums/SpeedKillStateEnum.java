package com.zxh.speedsale_system.enums;

import lombok.Getter;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 *
 * 秒杀结果状态的枚举返回对象
 */
@Getter
public enum SpeedKillStateEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATE_REWRITE(-3, "数据串改")
    ;

    private int state;
    private String stateInfo;

    SpeedKillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SpeedKillStateEnum stateOf(int index){
        for(SpeedKillStateEnum state : values()){
            if(state.getState() == index){
                return state;
            }
        }
        return null;
    }
}
