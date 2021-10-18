package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadYieldDemo
 * @author: HuangLang
 * @description: 线程的礼让  在线程操作中，也可以使用 yield() 方法将一个线程的操作暂时让给其他线程执行
 * @date: 2021-06-28 上午 10:23
 */
public class ThreadYieldDemo {
    public static void main(String[] args) {
        MyThread6 mt = new MyThread6();
        Thread t1 = new Thread(mt, "线程A");
        Thread t2 = new Thread(mt, "线程B");
        t1.start();
        t2.start();
    }
}

class MyThread6 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()
                    + "运行，i = " + i);  // 取得当前线程的名字
            if (i == 5) {
                System.out.print(Thread.currentThread().getName() + "礼让：");
                Thread.yield(); // 线程礼让
            }
        }
    }
}

