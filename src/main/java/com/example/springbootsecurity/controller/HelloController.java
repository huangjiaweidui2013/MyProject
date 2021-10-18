package com.example.springbootsecurity.controller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.example.springbootsecurity.config.MyConfigStaticTest;
import com.example.springbootsecurity.config.MyConfigTest;
import com.example.springbootsecurity.config.MyPropertiesTest;
import com.example.springbootsecurity.domain.BO.LoginUser;
import com.example.springbootsecurity.domain.VO.StudentRequestVO;
import com.example.springbootsecurity.notification.event.LoginUserEvent;
import com.example.springbootsecurity.task.MyTimerTask;
import com.example.springbootsecurity.utils.KaptchaCodeUtil;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: HelloController
 * @author: HuangLang
 * @description: hello
 * @date: 2021-05-28 上午 9:52
 */
@RestController
@AllArgsConstructor
public class HelloController {
    private MyConfigTest myConfigTest;
    private MyConfigStaticTest myConfigStaticTest;
    private MyPropertiesTest myPropertiesTest;
    private final ApplicationContext applicationContext;
    private final ApplicationEventPublisher eventPublisher;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request) {
        String nowString = formatter.format(LocalDateTime.now());
        System.out.println("controller方法当前时间: " + nowString);
        // http://localhost:8887/pages/hello.html
        if (!KaptchaCodeUtil.checkVerifyCode(request)) {
            eventPublisher.publishEvent(new LoginUserEvent(this, new LoginUser("验证码有误", "123456")));
            return "验证码有误！ 当前时间: " + nowString;
        } else {
            applicationContext.publishEvent(new LoginUserEvent(this, new LoginUser("验证码正确", "123456")));
            return "hello,world! 当前时间: " + nowString;
        }
    }

    @GetMapping("/ignoreTest")
    public String ignoreTest() {
        return "这个接口地址不走 Spring Security 过滤器链";
    }

    @GetMapping("/encode/{raw}")
    public String encode(@PathVariable String raw) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(raw);
    }

    @GetMapping("/config")
    public String getConfig() {
        String nowString = formatter.format(LocalDateTime.now());
        System.out.println("======================== HelloController getConfig 方法当前时间: " + nowString);
        executorService.schedule(MyTimerTask.firstTimerTask(), 10L, TimeUnit.MILLISECONDS);
        return myConfigTest.printConfig();
    }

    @GetMapping("/property")
    public String getProperty() {
        //输出为： MyPropertiesTest(name=huang, age=15, address=Wuhan, city=null, score=null, total=0, money=0.0)
        return myPropertiesTest.toString();
    }

    @GetMapping("/static/config")
    public String getStaticConfig() {
        Map<String, Object> map = Map.of("name", MyConfigStaticTest.getName(), "age", MyConfigStaticTest.getAge(),
                "addr", MyConfigStaticTest.getAddr());
        String join = Strings.join(map.entrySet(), ',');
        return join + " => " + MyConfigStaticTest.printConfig();
    }

    @PostMapping("/valid/domain")
    public String validStudent(@RequestBody @Valid StudentRequestVO requestVO) {
        return "SUCCESS";
    }

//    public static void main(String[] args) {
//        String password = "Peony@2021";
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encryptPassWord = encoder.encode(password);
//        System.out.println(encryptPassWord);
//        boolean matches = encoder.matches(password, "$2a$10$TwLizJfvFWfG4nxD4cl9Z.KKuosfb/dZqHqRsnDyuwjIRalot7Yo2");
//        System.out.println(matches);
//
//    }

    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "localhost";
        String dataId = "nacos-demo.yaml";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);

        Thread.sleep(3000);
        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);

        boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
        Thread.sleep(3000);

        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(300000);

    }
}
