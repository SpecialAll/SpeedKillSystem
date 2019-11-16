package com.zxh.speedsale_system.exception;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 *
 * 秒杀异常
 */
public class SpeedKillException extends RuntimeException {

    public SpeedKillException(String message){
        super(message);
    }

    public SpeedKillException(String message, Throwable cause){
        super(message, cause);
    }
}
