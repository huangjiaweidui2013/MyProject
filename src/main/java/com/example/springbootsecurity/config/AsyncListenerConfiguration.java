package com.example.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.config
 * @className: AsyncListenerConfiguration
 * @author: HuangLang
 * @description: 监听器设置异步线程池对象
 * @date: 2021-08-02 下午 5:20
 */
@Configuration
//@EnableAsync
public class AsyncListenerConfiguration implements AsyncConfigurer {
    /**
     * 异步通知的第一种方式:
     * 使用 ThreadPoolTaskExecutor , @EnableAsync注解 和 @Async注解
     *
     * @return
     */
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
//        poolTaskExecutor.setCorePoolSize(10);
//        poolTaskExecutor.setMaxPoolSize(20);
//        poolTaskExecutor.setQueueCapacity(50);
//        poolTaskExecutor.initialize();
//        return poolTaskExecutor;
//    }
//
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return null;
//    }

    /**
     * 异步通知的第二种方式:
     * 下面的这种方式也能实现异步通知
     *
     * @return
     */
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
}
