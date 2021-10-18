package com.example.springbootsecurity.notification.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.notification.event
 * @className: LoginUserEventAsyncListener
 * @author: HuangLang
 * @description: 登录事件监听
 * @date: 2021-08-02 下午 4:49
 */
@Slf4j
@Component
public class LoginUserEventAsyncListener {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

    //    @Async
//    @EventListener(condition = "#loginUserEvent.loginUser.userName == 'admin'")
    @EventListener
    public void sendEmail(LoginUserEvent loginUserEvent) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String userName = loginUserEvent.getLoginUser().getUserName();
        System.out.println(new Date());
        System.out.println("异步监听，模拟发送邮件给用户。。。" + userName + "当前时间: " + formatter.format(LocalDateTime.now()));
        log.info("异步监听，模拟发送邮件给用户{}, 当前时间: {}", userName, formatter.format(LocalDateTime.now()));
    }
}
