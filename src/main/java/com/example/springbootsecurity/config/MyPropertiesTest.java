package com.example.springbootsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.config
 * @className: MyPropertiesTest
 * @author: HuangLang
 * @description:
 * @date: 2021-07-06 下午 3:16
 */
@Component
@Data
@ConfigurationProperties(prefix = "sbs.property.test")
public class MyPropertiesTest {
    private String name;
    private Integer age;
    private String address;

    //配置文件中缺少配置项，不会报错;配置文件中没有配置的属性的值将会是默认值.
    //输出为： MyPropertiesTest(name=huang, age=15, address=Wuhan, city=null, score=null, total=0, money=0.0)
    private String city;
    private Double score;
    private int total;
    private double money;
}
