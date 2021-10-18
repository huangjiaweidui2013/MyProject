package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadJoinDemo
 * @author: HuangLang
 * @description: 线程的强制运行  在线程操作中，可以使用 join() 方法让一个线程强制运行，线程强制运行期间，其他线程无法运行，必须等待此线程完成之后才可以继续执行。
 * @date: 2021-06-28 上午 9:53
 */
public class ThreadJoinDemo {
    public static void main(String[] args) {
        MyThread1 mt = new MyThread1();// 实例化Runnable子类对象
        Thread t = new Thread(mt, "线程");// 实例化Thread对象
        t.start();// 启动线程
        for (int i = 0; i < 20; i++) {
            if (i > 10) {
                try {
                    t.join();// 线程强制运行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Main线程运行 --> " + i);
        }
    }
}

class MyThread1 implements Runnable { // 实现Runnable接口
    @Override
    public void run() {  // 覆写run()方法
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName()
                    + "运行，i = " + i);  // 取得当前线程的名字
        }
    }
}

