package com.example.springbootsecurity.demo.stop_watch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * @author localuser
 * create at 2022/4/20 11:34
 * @description 使用 SpringBoot 框架自带的 StopWatch
 */
@Slf4j
public class SpringBootStopWatch {
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Thread.sleep(1500);
        stopWatch.stop();
        log.info("第一段时间： {}", stopWatch.getTotalTimeMillis());
        log.info("第一段时间： {}", stopWatch.getLastTaskTimeMillis());
        stopWatch.start();
        Thread.sleep(2000);
        other();
        stopWatch.stop();
        log.info("第二段时间： {}", stopWatch.getTotalTimeMillis());
        log.info("第二段时间： {}", stopWatch.getLastTaskTimeMillis());
        stopWatch.start();
        Thread.sleep(3000);
        stopWatch.stop();
        log.info("第三段时间： {}", stopWatch.getTotalTimeMillis());
        log.info("第三段时间： {}", stopWatch.getLastTaskTimeMillis());
        stopWatch.start();
        Thread.sleep(1000);
        stopWatch.stop();
        log.info("第四段时间： {}", stopWatch.getTotalTimeMillis());
        log.info("第四段时间： {}", stopWatch.getLastTaskTimeMillis());
    }

    public static void other() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Thread.sleep(2000);
        log.info("other 方法执行时间： {}", stopWatch.getTotalTimeMillis());
    }
}
