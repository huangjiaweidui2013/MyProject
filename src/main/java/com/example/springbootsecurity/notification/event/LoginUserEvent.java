package com.example.springbootsecurity.notification.event;

import com.example.springbootsecurity.domain.BO.LoginUser;
import org.springframework.context.ApplicationEvent;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.notification.event
 * @className: LoginUserEvent
 * @author: HuangLang
 * @description: 登录事件
 * @date: 2021-08-02 下午 4:46
 */
public class LoginUserEvent extends ApplicationEvent {
    private final LoginUser user;

    public LoginUserEvent(Object source, LoginUser user) {
        super(source);
        this.user = user;
    }

    public LoginUser getLoginUser() {
        return user;
    }
}
