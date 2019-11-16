package com.zxh.speedsale_system.exception;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 */
public class SpeedKillCloseException extends SpeedKillException {
    public SpeedKillCloseException(String message) {
        super(message);
    }

    public SpeedKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
