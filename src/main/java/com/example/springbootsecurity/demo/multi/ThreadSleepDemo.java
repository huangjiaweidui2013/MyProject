package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadSleepDemo
 * @author: HuangLang
 * @description: 线程的休眠  在程序中允许一个线程进行暂时的休眠，直接使用 Thread.sleep() 即可实现休眠。
 * @date: 2021-06-28 上午 10:02
 */
public class ThreadSleepDemo {
    public static void main(String[] args) {
        MyThread2 mt = new MyThread2();
        Thread t = new Thread(mt, "线程");
        t.start();//启动线程
    }
}

class MyThread2 implements Runnable { // 实现Runnable接口
    public void run() {  // 覆写run()方法
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(500); // 线程休眠
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName()
                    + "运行，i = " + i);  // 取得当前线程的名字
        }
    }
}