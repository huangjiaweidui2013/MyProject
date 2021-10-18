package com.example.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.config
 * @className: SecurityConfig
 * @author: HuangLang
 * @description: security config
 * @date: 2021-05-28 上午 10:05
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 我们可以在 successHandler 方法中，配置登录成功的回调，如果是前后端分离开发的话，
     * 登录成功后返回 JSON 即可，同理，failureHandler 方法中配置登录失败的回调，
     * logoutSuccessHandler 中则配置注销成功的回调。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 开启登录配置
                .antMatchers("/hello").hasRole("admin")//表示访问 /hello 这个接口，需要具备 admin 这个角色
                .anyRequest().authenticated()//表示剩余的其他接口，登录之后就能访问
                .and()
                .formLogin()
//        .loginPage("/login_p")//定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
                .loginProcessingUrl("/doLogin")//登录处理接口
                .usernameParameter("uname")//定义登录时，用户名的 key，默认为 username
                .passwordParameter("passwd")//定义登录时，用户密码的 key，默认为 password
                //登录成功的处理器
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        Authentication authentication) throws IOException,
                            ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("success");
                        out.flush();
                    }
                })
                //登录失败的处理器
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        AuthenticationException e) throws IOException,
                            ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("fail");
                        out.flush();
                    }
                })
                .permitAll()//和表单登录相关的接口统统都直接通过
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("logout success");
                        out.flush();
                    }
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 下面这两行配置表示在内存中配置了两个用户。
         * 这里我们在 configure 方法中配置了两个用户，用户的密码都是加密之后的字符串(明文是 123)，从 Spring5 开始，
         * 强制要求密码要加密，如果非不想加密，可以使用一个过期的 PasswordEncoder 的实例 NoOpPasswordEncoder，但是不建议这么做，毕竟不安全。
         */
        auth.inMemoryAuthentication().withUser("admin1").roles("admin")
                .password("$2a$10$yzMm942QaXaQJI8HMaIM7undLd3.Dhl5k3fRtdlasXYFWAUhN6Py.").and().withUser("user1").roles("user")
                .password("$2a$10$VUFZyuLbh0b/dosnQF9Swuk7cQFf2oRF.9HrCX9jpKFyLW9APcwT.");


    }

    @Override
    public void configure(WebSecurity web) {
        /**
         * 如果某一个请求地址不需要拦截的话，有两种方式实现：
         *
         * 设置该地址匿名访问
         * 直接过滤掉该地址，即该地址不走 Spring Security 过滤器链
         * 推荐使用第二种方案，配置如下：
         */
        web.ignoring().antMatchers("/ignoreTest").antMatchers("/test");
        web.ignoring().antMatchers("/static/pages/**");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
