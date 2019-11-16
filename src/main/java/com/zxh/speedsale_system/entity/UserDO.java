package com.zxh.speedsale_system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 *
 * 用户信息实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {
    private long id;
    private long userPhone; //用户手机号
}
