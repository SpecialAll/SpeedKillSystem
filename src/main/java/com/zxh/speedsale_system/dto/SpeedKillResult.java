package com.zxh.speedsale_system.dto;

import lombok.*;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpeedKillResult<T> {
    private boolean success;

    private T data;

    private String error;

    public SpeedKillResult(boolean success, T data){
        this.data = data;
        this.success = success;
    }

    public SpeedKillResult(boolean success, String error){
        this.success = success;
        this.error = error;
    }
}
