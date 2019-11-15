package com.zxh.speedsale_system.mapper;

import com.zxh.speedsale_system.entity.GoodsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 *
 * 商品-数据库操作
 */
@Mapper
public interface SpeedKillGoodsMapper {
    /**
     * 查询所有秒杀商品的信息
     */
    List<GoodsDO> findAll();

    /**
     * 查询当前商品的数据
     *
     * @param id
     */
    GoodsDO findById(@Param("id") long id);

    /**
     * 减库存操作
     *
     * @param id   商品id
     * @param killTime   秒杀创建时间
     * @return
     */
    int reduceStock(@Param("id") long id, @Param("killTime") Date killTime);
}
