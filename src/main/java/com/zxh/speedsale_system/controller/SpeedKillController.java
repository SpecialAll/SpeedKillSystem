package com.zxh.speedsale_system.controller;

import com.zxh.speedsale_system.dto.Exposer;
import com.zxh.speedsale_system.dto.SpeedKillExecution;
import com.zxh.speedsale_system.dto.SpeedKillResult;
import com.zxh.speedsale_system.entity.GoodsVO;
import com.zxh.speedsale_system.enums.SpeedKillStateEnum;
import com.zxh.speedsale_system.exception.RepeatKillException;
import com.zxh.speedsale_system.exception.SpeedKillCloseException;
import com.zxh.speedsale_system.exception.SpeedKillException;
import com.zxh.speedsale_system.service.SpeedKillService;
import com.zxh.speedsale_system.service.SpeedKillUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 *
 * 秒杀活动控制层 —— controller
 */
@Controller
@RequestMapping("/speedKill")
@Slf4j
public class SpeedKillController {
    @Autowired
    private SpeedKillService speedKillService;
    @Autowired
    private SpeedKillUserService userService;

    @RequestMapping("/list")
    public String findSpeedKillGoodsList(Model model){
        List<GoodsVO> list = speedKillService.findAll();
        model.addAttribute("list",list);
        return "/page/speedKill";
    }

//    @RequestMapping("/index")
//    public String getIndex(Model model){
//        return "/page/index";
//    }

    @ResponseBody
    @RequestMapping("/findById")
    public GoodsVO findById(@RequestParam("id") Long id){
        return speedKillService.findById(id);
    }

    @RequestMapping("/{goodsId}/detail")
    public String detail(@PathVariable("goodsId") Long goodsId, Model model){
        if(goodsId == null){
            return "/page/speedKill";
        }
        GoodsVO speedKill = speedKillService.findById(goodsId);
        model.addAttribute("speedKill", speedKill);
        if(speedKill == null){
            return "/page/speedKill";
        }
        return "/page/speedKill_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/{goodsId}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public SpeedKillResult<Exposer> exposer(@PathVariable("goodsId") Long goodsId){
        SpeedKillResult<Exposer> result;
        try {
            Exposer exposer = speedKillService.exportSpeedKillUrl(goodsId);
            result = new SpeedKillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result = new SpeedKillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{goodsId}/{md5}/execution",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    public SpeedKillResult<SpeedKillExecution> execute(@PathVariable("goodsId") Long goodsId,
                                                       @PathVariable("md5") String md5,
                                                       @RequestParam("money") BigDecimal money,
                                                       @CookieValue(value = "killPhone", required = false) Long userPhone ){
        if(userPhone == null){
            return new SpeedKillResult<SpeedKillExecution>(false, "未注册");
        }
        try {
            long userId = (userService.getUser(userPhone)).getId();
            SpeedKillExecution execution = speedKillService.executeSpeedKill(goodsId,money,userId,md5);
            return new SpeedKillResult<SpeedKillExecution>(true, execution);
        } catch (RepeatKillException e) {
            SpeedKillExecution execution = new SpeedKillExecution(goodsId, SpeedKillStateEnum.REPEAT_KILL);
            return new SpeedKillResult<SpeedKillExecution>(true,execution);
        } catch (SpeedKillCloseException e) {
            SpeedKillExecution execution = new SpeedKillExecution(goodsId, SpeedKillStateEnum.END);
            return new SpeedKillResult<SpeedKillExecution>(true,execution);
        } catch (SpeedKillException e) {
            SpeedKillExecution execution = new SpeedKillExecution(goodsId, SpeedKillStateEnum.INNER_ERROR);
            return new SpeedKillResult<SpeedKillExecution>(true,execution);
        }
    }

    @ResponseBody
    @GetMapping("/time/now")
    public SpeedKillResult<Long> time(){
        //获取当前服务器时间，用于不同时区的用户操作
        Date date = new Date();
        return new SpeedKillResult(true, date.getTime());
    }
}
