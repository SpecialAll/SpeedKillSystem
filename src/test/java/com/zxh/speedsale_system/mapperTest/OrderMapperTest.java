package com.zxh.speedsale_system.mapperTest;

import com.zxh.speedsale_system.entity.OrderDetailDO;
import com.zxh.speedsale_system.mapper.SpeedKillOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderMapperTest {
    @Autowired
    SpeedKillOrderMapper orderMapper;

    @Test
    public void insertOrder(){
        int ans = orderMapper.insertOrder(6, BigDecimal.valueOf(150.00), 1);
        System.out.println(ans);
    }

    @Test
    public void findByUserId(){
        List<OrderDetailDO> list = orderMapper.findByUserId(2);
        for(OrderDetailDO order : list){
            System.out.println(order.toString());
        }
    }
}
