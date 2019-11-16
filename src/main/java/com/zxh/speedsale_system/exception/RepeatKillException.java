package com.zxh.speedsale_system.exception;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 */
public class RepeatKillException extends SpeedKillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
