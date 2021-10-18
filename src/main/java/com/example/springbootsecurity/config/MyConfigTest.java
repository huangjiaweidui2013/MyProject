package com.example.springbootsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.config
 * @className: MyConfigTest
 * @author: HuangLang
 * @description: 测试读取配置文件中的数据
 * @date: 2021-06-11 上午 11:23
 */
@Component
@Slf4j
@RefreshScope
public class MyConfigTest {
    //    @Value("${my.config.test.name:Lang}")
    @Value("${my.config.test.name}")
    private String name;

    //    @Value(value = "${my.config.test.age:18}")
    @Value(value = "${my.config.test.age}")
    private Integer age;

    //    @Value(value = "${my.config.test.addr:Japan}")
    @Value(value = "${my.config.test.addr}")
    private String addr;

    /**
     * 方法
     *
     * @Author huang lang
     * @Date 下午 5:25 2021-07-06
     * @Param
     * @Return
     */
    public String printConfig() {
        log.info("MyConfigTest: name={}, age={}, addr={}", name, age, addr);
        return String.format("name: %s, age: %d, addr: %s", name, age, addr);
    }
}
