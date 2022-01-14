package com.example.springbootsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.config
 * @className: MyConfigStaticTest
 * @author: HuangLang
 * @description: 测试使用静态变量读取配置文件中的数据
 * @date: 2021-06-11 上午 11:23
 */
@Component
@Slf4j
public class MyConfigStaticTest {

    private static String name;

    private static Integer age;

    private static String addr;

    /**
     * 1.原因分析
     * 在后端开发当中我们可能会使用到工具类,而一般的工具类中的方法都是静态方法。而@Value注解只能给普通变量注入值，不能直接给静态变量赋值。
     * <p>
     * 2.延伸
     * 静态变量(即类变量)是一个类的属性，而不是对象的属性。spring依赖注入是基于对象层面上的。
     * <p>
     * static变量在对象创建前就分配过内存了，而spring的注入是在创建对象的时候注入的。
     * <p>
     * 3.解决方法
     * set方法是非静态的。
     * ————————————————
     * 版权声明：本文为CSDN博主「hi_link」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/qq_28433521/article/details/114885846
     *
     * @param name
     */
    @Value("${my.config.test.name:Lang}")
    public void setName(String name) {
        MyConfigStaticTest.name = name;
    }

    /**
     * 注意set方法必须是非静态的
     *
     * @param age
     */
    @Value(value = "${my.config.test.age:18}")
    public void setAge(Integer age) {
        MyConfigStaticTest.age = age;
    }

    @Value(value = "${my.config.test.addr:Japan}")
    public void setAddr(String addr) {
        MyConfigStaticTest.addr = addr;
    }

    public static String printConfig() {
        log.info("MyConfigTest: name={}, age={}, addr={}", name, age, addr);
        return String.format("name: %s, age: %d, addr: %s", name, age, addr);
    }

    public static String getName() {
        return name;
    }

    public static Integer getAge() {
        return age;
    }

    public static String getAddr() {
        return addr;
    }
}
