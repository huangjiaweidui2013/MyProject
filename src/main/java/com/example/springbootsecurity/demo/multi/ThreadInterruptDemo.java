package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadInterruptDemo
 * @author: HuangLang
 * @description: 中断线程  当一个线程运行时，另外一个线程可以直接通过interrupt()方法中断其运行状态。
 * @date: 2021-06-28 上午 10:05
 */
public class ThreadInterruptDemo {
    public static void main(String[] args) {
        MyThread3 mt = new MyThread3();
        Thread t = new Thread(mt, "线程");
        t.start();
        try {
            Thread.sleep(2000);// 线程休眠2秒
        } catch (InterruptedException e) {
            System.out.println("3、休眠被终止");
        }
        t.interrupt(); // 中断线程执行
    }
}

class MyThread3 implements Runnable { // 实现Runnable接口
    public void run() {  // 覆写run()方法
        System.out.println("1、进入run()方法");
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(10000);   // 线程休眠10秒
                System.out.println("2、已经完成了休眠");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "  3、休眠被终止");
                return; // 返回调用处
            }
            System.out.println("4、run()方法正常结束");
        }
    }
}
