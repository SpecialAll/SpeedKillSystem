package com.zxh.speedsale_system.ServiceTest;

import com.zxh.speedsale_system.dto.Exposer;
import com.zxh.speedsale_system.dto.SpeedKillExecution;
import com.zxh.speedsale_system.entity.GoodsVO;
import com.zxh.speedsale_system.service.SpeedKillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 *
 * 秒杀服务业务层测试代码
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SpeedKillServiceTest {
    @Autowired
    private SpeedKillService speedKillService;

    @Test
    public void findAll(){
        List<GoodsVO> list = speedKillService.findAll();
        for(GoodsVO goods : list){
            System.out.println(goods.toString());
        }
    }

    @Test
    public void findById(){
        GoodsVO goodsVO = speedKillService.findById(6);
        System.out.println(goodsVO.toString());
    }

    @Test
    public void exportSpeedKillUrl(){
        Exposer exposer = speedKillService.exportSpeedKillUrl(6);
        System.out.println(exposer.toString());
    }

    @Test
    public void executeSpeedKill(){
        Exposer exposer = speedKillService.exportSpeedKillUrl(5);
        SpeedKillExecution execution = speedKillService.executeSpeedKill(5, BigDecimal.valueOf(1100.00), 1, exposer.getMd5());
        System.out.println(execution.toString());
    }
}
