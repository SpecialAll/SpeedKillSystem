package com.zxh.speedsale_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 *
 * 秒杀暴露地址 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exposer {
    //是否开启秒杀
    private boolean exposed;

    //加密措施  避免用户通过抓包拿到秒杀地址
    private String md5;

    //秒杀商品id
    private long killGoodsId;

    //秒杀开始时间
    private long startTime;

    //秒杀结束时间
    private long endTime;

    //系统当前时间
    private long nowTime;

    public Exposer(boolean exposed, long killGoodsId){
        this.exposed = exposed;
        this.killGoodsId = killGoodsId;
    }

    public Exposer(boolean exposed, long killGoodsId, long startTime, long endTime, long nowTime){
        this.killGoodsId = killGoodsId;
        this.exposed = exposed;
        this.endTime = endTime;
        this.startTime = startTime;
        this.nowTime = nowTime;
    }

    public Exposer(boolean exposed, String md5, long killGoodsId){
        this.exposed = exposed;
        this.killGoodsId = killGoodsId;
        this.md5 = md5;
    }
}
