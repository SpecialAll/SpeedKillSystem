package com.zxh.speedsale_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/16
 *
 * 用于做页面跳转的控制层
 */
@Controller
public class BaseController {
    /**
     * 跳转到秒杀商品页
     */
    @RequestMapping("/speedKill")
    public String speedKillGoods(){
        return "redirect:/speedKill/list";
    }
}
