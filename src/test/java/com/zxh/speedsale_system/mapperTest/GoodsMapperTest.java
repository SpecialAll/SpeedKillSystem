package com.zxh.speedsale_system.mapperTest;

import com.zxh.speedsale_system.entity.GoodsDO;
import com.zxh.speedsale_system.mapper.SpeedKillGoodsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GoodsMapperTest {

    @Autowired
    private SpeedKillGoodsMapper goodsMapper;

    @Test
    public void findAll(){
        List<GoodsDO> list = goodsMapper.findAll();
        for (GoodsDO goods : list) {
            System.out.println(goods.getGoodsTitle());
        }
    }

    @Test
    public void findById(){
        GoodsDO goodsDO = goodsMapper.findById(6);
        System.out.println(goodsDO.getGoodsTitle());
    }

    @Test
    public void reduceStock(){
        int ans = goodsMapper.reduceStock(5, new Date());
        System.out.println(ans);
    }
}
