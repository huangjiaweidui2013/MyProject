package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadWaitNotifyDemo
 * @author: HuangLang
 * @description: 学习wait notify用法
 * @date: 2021-07-06 上午 9:05
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        Object lock = new Object();
        WaitThread t1 = new WaitThread(lock);
        t1.start();
        NotifyThread t2 = new NotifyThread(lock);
        t2.start();
    }

}

class NotifyThread extends Thread {
    private final Object lock;

    NotifyThread(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            // 做一些业务逻辑相关的事。。。。
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 业务逻辑完成了...
            System.out.println("开始 notify time= " + System.currentTimeMillis());
            lock.notify();
            System.out.println("结束 notify time= " + System.currentTimeMillis());
        }
    }
}

class WaitThread extends Thread {
    private final Object lock;

    WaitThread(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                long start = System.currentTimeMillis();
                System.out.println("开始 wait time= " + start);
                lock.wait();
                long end = System.currentTimeMillis();
                System.out.println("结束 wait time= " + end);
                System.out.print("wait time = " + (end - start));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
