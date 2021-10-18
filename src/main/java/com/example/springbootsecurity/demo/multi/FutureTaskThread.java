package com.example.springbootsecurity.demo.multi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: FutureTaskThread
 * @author: HuangLang
 * @description: 使用FutureTask创建线程
 * @date: 2021-06-28 下午 2:24
 */
public class FutureTaskThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable call = new MyCallable();
        FutureTask task = new FutureTask(call);
        Thread th = new Thread(task, "task线程");
        th.start();
        Object o = task.get();
        System.out.println("result: " + o);

        FutureTask<Boolean> ft = new FutureTask<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(ft);
        executor.shutdown();
        System.out.println(ft.get());
    }
}

class MyCallable implements Callable {

    @Override
    public Object call() throws Exception {
        int i = 100;
        System.out.println("callable call 方法返回值: " + i);
        return i;
    }
}
