package com.example.springbootsecurity.startup;

import com.example.springbootsecurity.service.MyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.startup
 * @className: MyApplicationRunner
 * @author: HuangLang
 * @description: 实现ApplicationRunner接口启动
 * 在SpringBoot项目中创建组件类实现CommandLineRunner或ApplicationRunner
 * 接口可实现在应用启动之后及时进行一些初始化操作，如缓存预热、索引重建等等类似一些数据初始化操作。两个接口功能相同，都有个run方法需要重写，只是实现方法的参数不同。CommandLineRunner
 * 接收原始的命令行启动参数，ApplicationRunner则将启动参数对象化。
 * 当项目中同时实现了ApplicationRunner和CommondLineRunner接口时，可使用Order注解或实现Ordered接口来指定执行顺序，值越小越先执行。
 * ApplicationListener< ContextRefreshedEvent> 不推荐
 * ApplicationListener  推荐
 * CommandLineRunner 推荐
 * @date: 2021-07-15 下午 2:39
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    private MyTestService myTestService;

    @Autowired
    public void setMyTestService(MyTestService myTestService) {
        this.myTestService = myTestService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        myTestService.ThreadPrintRandom();
    }
}
