package com.example.springbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * spring boot 启动后执行自定义方法的方式：
 * 如果实现了多个Runner，默认会按照添加顺序先执行ApplicationRunner的实现再执行CommandLineRunner的实现，如果多个Runner之间的初始化逻辑有先后顺序，可在实现类添加@Order
 * 注解设置执行顺序，可在源码SpringApplication类的callRunners方法中查看
 * ————————————————
 * 版权声明：本文为CSDN博主「晴空排云」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/lxh_worldpeace/article/details/105854497
 */

/**
 * jar 包加载外部的application配置文件，从下面的位置寻找,越是下面的优先级越高
 * 1.From the classpath
 * 1.1 The classpath root
 * 1.2 The classpath /config package
 * 2.From the current directory
 * 2.1 The current directory
 * 2.2 The /config subdirectory in the current directory
 * 2.3 Immediate child directories of the /config subdirectory
 */
// 在使用 java 代码 KaptchaConfig 来配置 kaptcha 的情况下,要去掉启动类中引入的.xml文件，不然会有两个相同的对象，而你没有指明要注入哪一个的话启动会失败
//@ImportResource(locations = {"classpath:kaptcha/kaptcha.xml"})
@SpringBootApplication
public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        /**
         * Spring Security 中提供了 BCryptPasswordEncoder 密码编码工具，可以非常方便的实现密码的加密加盐，
         * 相同明文加密出来的结果总是不同，这样就不需要用户去额外保存盐的字段了，这一点比 Shiro 要方便很多。
         */
        String encode1 = passwordEncoder.encode("123");
        String encode2 = passwordEncoder.encode("123");
        String encode3 = passwordEncoder.encode("123");
        String encode4 = passwordEncoder.encode("123");
        String encode5 = passwordEncoder.encode("123");
        // boolean matches(CharSequence rawPassword, String encodedPassword);
        // rawPassword 原生明文密码 , encodedPassword 加密后的密码字符串
        System.out.println("encode1: " + encode1 + "  ===match: " + passwordEncoder.matches("123", encode1));
        System.out.println("encode2: " + encode2 + "  ===match: " + passwordEncoder.matches("123", encode2));
        System.out.println("encode3: " + encode3 + "  ===match: " + passwordEncoder.matches("123", encode3));
        System.out.println("encode4: " + encode4 + "  ===match: " + passwordEncoder.matches("123", encode4));
        System.out.println("encode5: " + encode5 + "  ===match: " + passwordEncoder.matches("123", encode5));
    }

    /**
     * SpringBoot中Bean的加载过程，简单点说就是SpringBoot会把标记了Bean相关注解（例如@Component、@Service、@Repository
     * 等）的类或接口自动初始化全局的单一实例，如果标记了初始化顺序会按照用户标记的顺序，否则按照默认顺序初始化。在初始化的过程中，执行完一个Bean的构造方法后会执行该Bean的@PostConstruct
     * 方法（如果有），然后初始化下一个Bean。
     *
     * 那么： 如果@PostConstruct方法内的逻辑处理时间较长，就会增加SpringBoot应用初始化Bean的时间，进而增加应用启动的时间。因为只有在Bean初始化完成后，SpringBoot
     * 应用才会打开端口提供服务，所以在此之前，应用不可访问。
     * ————————————————
     * 版权声明：本文为CSDN博主「晴空排云」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/lxh_worldpeace/article/details/106789546
     *
     * 如果应用有一些初始化操作，有以下几点建议：
     *
     * 轻量的逻辑可放在Bean的@PostConstruct方法中
     * 耗时长的逻辑如果放在@PostConstruct方法中，可使用独立线程执行
     * 初始化操作放在CommandLineRunner或ApplicationRunner的实现组件中1
     */
}
