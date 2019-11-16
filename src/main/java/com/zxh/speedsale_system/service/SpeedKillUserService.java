package com.zxh.speedsale_system.service;

import com.zxh.speedsale_system.entity.UserDO;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 */
public interface SpeedKillUserService {
    /**
     * 用户注册
     * @param userPhone
     */
    int userLogin(long userPhone);

    /**
     * 用户信息获取
     */
    UserDO getUser(long userPhone);
}
