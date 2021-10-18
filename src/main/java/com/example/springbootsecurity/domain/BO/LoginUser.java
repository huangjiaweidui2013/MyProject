package com.example.springbootsecurity.domain.BO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.domain.BO
 * @className: LoginUser
 * @author: HuangLang
 * @description: 登录用户
 * @date: 2021-08-02 下午 4:44
 */
@Data
@AllArgsConstructor
public class LoginUser {
    private String userName;
    private String password;
}
