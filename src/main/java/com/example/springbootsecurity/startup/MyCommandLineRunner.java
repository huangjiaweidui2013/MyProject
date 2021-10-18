package com.example.springbootsecurity.startup;

import com.example.springbootsecurity.service.MyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 在SpringBoot项目中创建组件类实现CommandLineRunner或ApplicationRunner
 * 接口可实现在应用启动之后及时进行一些初始化操作，如缓存预热、索引重建等等类似一些数据初始化操作。两个接口功能相同，都有个run方法需要重写，只是实现方法的参数不同。CommandLineRunner
 * 接收原始的命令行启动参数，ApplicationRunner则将启动参数对象化。
 * ————————————————
 * 版权声明：本文为CSDN博主「晴空排云」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/lxh_worldpeace/article/details/105854497
 */

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.startup
 * @className: MyCommandLineRunner
 * @author: HuangLang
 * @description: springboot启动后执行某个方法的三种实现方式：监听接口方式，启动服务，执行方式时仍然提供服务，服务初始化之后，执行方法
 * 在SpringBoot应用初始化好上下文之后运行，所以在运行过程中，可以使用上下文中的所有信息，例如一些Bean等等。
 * @date: 2021-07-15 上午 11:33
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private MyTestService myTestService;

    @Autowired
    public void setMyTestService(MyTestService myTestService) {
        this.myTestService = myTestService;
    }

    public MyTestService getMyTestService() {
        return myTestService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunner run 方法开始执行...");
        myTestService.ThreadPrintInfo();
    }
}
