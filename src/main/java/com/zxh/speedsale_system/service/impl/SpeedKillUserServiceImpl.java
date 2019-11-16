package com.zxh.speedsale_system.service.impl;

import com.zxh.speedsale_system.entity.UserDO;
import com.zxh.speedsale_system.mapper.SpeedKillUserMapper;
import com.zxh.speedsale_system.service.SpeedKillUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 */
@Service
public class SpeedKillUserServiceImpl implements SpeedKillUserService {
    @Autowired
    private SpeedKillUserMapper userMapper;

    @Override
    public int userLogin(long userPhone) {
        return userMapper.loginUser(userPhone);
    }

    @Override
    public UserDO getUser(long userPhone) {
        return userMapper.getUser(userPhone);
    }
}
