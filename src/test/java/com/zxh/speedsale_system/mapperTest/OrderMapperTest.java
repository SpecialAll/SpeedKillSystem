package com.zxh.speedsale_system.mapperTest;

import com.zxh.speedsale_system.entity.OrderDetailDO;
import com.zxh.speedsale_system.mapper.SpeedKillOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderMapperTest {
    @Autowired
    private SpeedKillOrderMapper orderMapper;

    @Test
    public void insertOrder(){
        int ans = orderMapper.insertOrder(6, BigDecimal.valueOf(150.00), 1);
        System.out.println(ans);
    }

    @Test
    public void findByUserId(){
        OrderDetailDO order = orderMapper.findByUserIdAndGoodsId(2,6);
            System.out.println(order.toString());

    }
}
