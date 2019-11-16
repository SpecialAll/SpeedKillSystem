package com.zxh.speedsale_system.mapper;

import com.zxh.speedsale_system.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 */
@Mapper
public interface SpeedKillUserMapper {
    /**
     * 用户注册
     */
    int loginUser(@Param("userPhone") long userPhone);

    /**
     * 获取当前用户信息
     */
    UserDO getUser(@Param("userPhone") long userPhone);
}
