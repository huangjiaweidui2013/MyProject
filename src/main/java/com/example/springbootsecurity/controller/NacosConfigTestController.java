package com.example.springbootsecurity.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: NacosConfigTestController
 * @author: HuangLang
 * @description: 测试nacos config
 * @date: 2021-08-18 下午 6:33
 */
@RestController
@RefreshScope
public class NacosConfigTestController {
    @Value("${nacos.config.testStr}")
    private String testStr;

    @GetMapping("/nacos/test")
    public String test() {
        return testStr;
    }
}
