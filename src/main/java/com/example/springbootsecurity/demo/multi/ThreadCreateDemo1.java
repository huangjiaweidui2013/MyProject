package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadCreateDemo1
 * @author: HuangLang
 * @description: 在 Java 中实现多线程有两种手段，一种是继承 Thread 类，另一种就是实现 Runnable 接口。
 * 下面我们就分别来介绍这两种方式的使用。建议使用实现Runable接口方式。解决单继承的局限性。
 * @date: 2021-06-28 上午 9:17
 */
public class ThreadCreateDemo1 {
    public static ThreadLocal<String> integerThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        integerThreadLocal.set("测试父子线程传递ThreadLocal变量值");
        Runnable myRunnable1 = new MyRunnable();// 实例化对象
        Runnable myRunnable2 = new MyRunnable();// 实例化对象
        Thread thread1 = new Thread(myRunnable1, "线程A");// 实例化Thread类对象,手工设置线程名称
        Thread thread2 = new Thread(myRunnable2, "线程B");// 实例化Thread类对象,手工设置线程名称
        Thread thread3 = new Thread(myRunnable2);// 系统自动设置线程名称
        thread1.start();// 启动多线程
        thread2.start();// 启动多线程
        thread3.start();// 启动多线程

//        ThreadLocal<String> localName = new ThreadLocal<>();
//        localName.set("zhang san");
//        String name = localName.get();
//        localName.remove();
//
//        final ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
//        threadLocal.set("真帅");
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                System.out.println("张三帅吗? " + threadLocal.get());
//            }
//        };
//        t.start();
    }

    // 实现Runnable接口，作为线程的实现类
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "运行, i = " + i);
                System.out.println("MyRunnable: " + integerThreadLocal.get());
            }
        }
    }
}

