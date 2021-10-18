package com.example.springbootsecurity.service.impl;

import com.example.springbootsecurity.service.MyTestService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.service.impl
 * @className: MyTestServiceImpl
 * @author: HuangLang
 * @description: MyTestService 实现类
 * @date: 2021-07-13 下午 4:25
 */
@Service
public class MyTestServiceImpl implements MyTestService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String getLocalDateTime() {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * springboot启动后执行某个方法的三种实现方式:第一种方式，应该也是最简单的方式
     * 直接在方式上面注入，但是会影响服务提供，比如这个方法要执行五分钟 这五分钟之内是无法提供服务的，
     * 这个方法是在服务初始化后之前运行， 所以 此方法运行不结束，服务就无法初始化， 在这过程路也无法提供服务
     * <p>
     * SpringBoot中Bean的加载过程，简单点说就是SpringBoot会把标记了Bean相关注解（例如@Component、@Service、@Repository
     * 等）的类或接口自动初始化全局的单一实例，如果标记了初始化顺序会按照用户标记的顺序，否则按照默认顺序初始化。在初始化的过程中，执行完一个Bean的构造方法后会执行该Bean的@PostConstruct
     * 方法（如果有），然后初始化下一个Bean。
     * <p>
     * 那么： 如果@PostConstruct方法内的逻辑处理时间较长，就会增加SpringBoot应用初始化Bean的时间，进而增加应用启动的时间。因为只有在Bean初始化完成后，SpringBoot
     * 应用才会打开端口提供服务，所以在此之前，应用不可访问。
     * <p>
     * 如果应用有一些初始化操作，有以下几点建议：
     * <p>
     * 轻量的逻辑可放在Bean的@PostConstruct方法中
     * 耗时长的逻辑如果放在@PostConstruct方法中，可使用独立线程执行
     * 初始化操作放在CommandLineRunner或ApplicationRunner的实现组件中
     */
    @PostConstruct
    public void postMethod() {
        System.out.println("@PostConstruct 注解的方法开始执行...线程暂停5秒...");
        try {
            Thread.sleep(5000);
            System.out.println("@PostConstruct 注解的方法已经执行完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ThreadPrintInfo() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("MyTestServiceImpl 打印当前时间： " + this.getLocalDateTime());
            }
        }).start();
    }

    @Override
    public void ThreadPrintRandom() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("MyTestServiceImpl 打印随机数： " + Math.random());
            }
        }).start();
    }
}
