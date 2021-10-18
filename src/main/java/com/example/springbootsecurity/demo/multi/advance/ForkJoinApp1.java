package com.example.springbootsecurity.demo.multi.advance;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi.advance
 * @className: ForkJoinApp1
 * @author: HuangLang
 * @description: 学习jdk7新增的ForkJoin线程池
 * @date: 2021-06-29 上午 10:04
 */
public class ForkJoinApp1 {
    /**
     * 目标： 打印0-200以内的数字，进行分段每个间隔为10以上，测试forkjoin
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程池
        ForkJoinPool joinPool = new ForkJoinPool();
        // 创建根任务
        SubTask subTask = new SubTask(0, 200);
        // 提交任务
        joinPool.submit(subTask);
        //让线程阻塞等待所有任务完成 在进行关闭
        try {
            joinPool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        joinPool.shutdown();


        // 创建线程池
        ForkJoinPool joinPool2 = new ForkJoinPool();
        int n = 20;
        // 计算第20项的数值
        ForkJoinTask<Integer> submit = joinPool2.submit(new Fibonacci(n));
        // 获取结果，这里获取的就是异步任务的最终结果
        System.out.println("斐波那契数列 第 " + n + " 项是: " + submit.get());
        joinPool2.shutdown();
    }
}

class SubTask extends RecursiveAction {
    int startNum;
    int endNum;

    public SubTask(int startNum, int endNum) {
        super();
        this.startNum = startNum;
        this.endNum = endNum;
    }

    @Override
    protected void compute() {
        if (endNum - startNum < 10) {
            // 如果分裂的两者差值小于10 则不再继续，直接打印
            System.out.println(Thread.currentThread().getName() + ": [startNum:" + startNum + ",endNum:" + endNum +
                    "]");
        } else {
            // 取中间值
            int middle = (startNum + endNum) / 2;
            //创建两个子任务，以递归思想，
            SubTask subTask1 = new SubTask(startNum, middle);
            SubTask subTask2 = new SubTask(middle, endNum);
            //执行任务， fork() 表示异步的开始执行
            subTask1.fork();
            subTask2.fork();
        }
    }
}

class Fibonacci extends RecursiveTask<Integer> {
    int num;

    public Fibonacci(int num) {
        this.num = num;
    }

    @Override
    protected Integer compute() {
        if (num <= 1) {
            return num;
        }
        //创建子任务
        Fibonacci subTask1 = new Fibonacci(num - 1);
        Fibonacci subTask2 = new Fibonacci(num - 2);
        // 执行子任务
        subTask1.fork();
        subTask2.fork();
        //获取前两项的结果来计算和
        return subTask1.join() + subTask2.join();
    }
}
