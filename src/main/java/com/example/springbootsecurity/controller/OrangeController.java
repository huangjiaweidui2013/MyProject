package com.example.springbootsecurity.controller;

import com.example.springbootsecurity.domain.BO.OrangeBO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: OrangeController
 * @author: HuangLang
 * @description: 橘子 controller
 * @date: 2021-07-23 下午 5:56
 */
@RestController
@RequestMapping("/orange")
public class OrangeController {
    @GetMapping("/index")
    public String index() {
        OrangeBO orangeBO = OrangeBO.builder()
                .id(100)
                .price(1.50)
                .producedDate(new Date())
                .color("red")
                .build();
        return orangeBO.toString();
    }
}
