package com.example.springbootsecurity.demo.multi.advance;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi.advance
 * @className: ScheduledExecutorDemo
 * @author: HuangLang
 * @description: 学习scheduled线程池用法
 * @date: 2021-06-29 上午 9:19
 */
public class ScheduledExecutorDemo {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        // 1. 延迟一定时间执行一次
        service.schedule(() -> System.out.println("schedule 延迟2秒执行一次"), 2, TimeUnit.SECONDS);
        Callable<Double> call = () -> {
            double d = Math.random();
            System.out.println("schedule 延迟3秒执行一次 获取随机数" + d);
            return d;
        };
        ScheduledFuture<Double> future = service.schedule(call, 3, TimeUnit.SECONDS);
        try {
            System.out.println("call 返回： " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //2. 按照固定延迟周期执行
        service.scheduleWithFixedDelay(() -> System.out.println("schedule 2秒后第一次执行," +
                "任务执行完成3秒后,再执行下一次,周期执行 "
                + Math.random()), 2, 3, TimeUnit.SECONDS);

        //3. 按照固定频率周期执行
        /**
         * scheduleAtFixedRate 是以任务开始的时间为时间起点来计时，时间到就执行第二次任务，与任务执行所花费的时间无关；
         * 而 scheduleWithFixedDelay 是以任务执行结束的时间点作为计时的开始。
         */
        service.scheduleAtFixedRate(() -> System.out.println("schedule 2秒后开始第一次执行,从此刻开始," +
                        "3秒后开始下一次执行,无论前一次任务是否执行完毕,周期执行"),
                2, 3, TimeUnit.SECONDS);
    }
}
