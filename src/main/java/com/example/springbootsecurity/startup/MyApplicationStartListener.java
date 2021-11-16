package com.example.springbootsecurity.startup;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.startup
 * @className: MyApplicationStartListener
 * @author: HuangLang
 * @description: springboot启动后执行某个方法的三种实现方式：通过监听接口方式启动，服务已经初始化过，不影响 服务启动，并且启动之后可以正常提供服务
 * @date: 2021-07-13 下午 4:13
 */
@Component
public class MyApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        System.out.println("MyApplicationStartListener onApplicationEvent 方法开始执行...");
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(5000);
//                    System.out.println("MyApplicationStartListener 打印当前时间: " + LocalDateTime.now());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
