package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadDaemonDemo
 * @author: HuangLang
 * @description: 后台线程  在 Java 程序中，只要前台有一个线程在运行，则整个 Java 进程都不会消失，所以此时可以设置一个后台线程，这样即使 Java
 * 线程结束了，此后台线程依然会继续执行，要想实现这样的操作，直接使用 setDaemon() 方法即可。
 * @date: 2021-06-28 上午 10:16
 */
public class ThreadDaemonDemo {
    public static void main(String[] args) {
        MyThread4 mt = new MyThread4();
        Thread t = new Thread(mt, "线程");
        t.setDaemon(true);// 此线程在后台运行 守护线程
        t.start();
    }
}

class MyThread4 implements Runnable { // 实现Runnable接口
    public void run() {  // 覆写run()方法
        while (true) {
            System.out.println(Thread.currentThread().getName() + "在运行。");
        }
    }
}