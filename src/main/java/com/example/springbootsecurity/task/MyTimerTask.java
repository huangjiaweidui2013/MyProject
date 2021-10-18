package com.example.springbootsecurity.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.task
 * @className: MyTimerTask
 * @author: HuangLang
 * @description: JAVA定时任务TimerTask
 * @date: 2021-08-20 上午 10:20
 */
public class MyTimerTask {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

    public static TimerTask firstTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("进入MyTimerTask firstTimerTask定时任务,开始执行,线程暂停10秒钟...");
                    Thread.sleep(1000L * 10);
                    String nowString = formatter.format(LocalDateTime.now());
                    System.out.println("MyTimerTask firstTimerTask定时任务执行完毕,前时间: " + nowString);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
