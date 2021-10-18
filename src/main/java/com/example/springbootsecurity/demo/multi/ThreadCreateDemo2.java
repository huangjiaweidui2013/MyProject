package com.example.springbootsecurity.demo.multi;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi
 * @className: ThreadCreateDemo2
 * @author: HuangLang
 * @description: 继承Thread类实现多线程
 * @date: 2021-06-28 上午 9:24
 */
public class ThreadCreateDemo2 {
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("线程A");// 实例化对象
        MyThread myThread2 = new MyThread("线程B");// 实例化对象
        myThread1.start();
        myThread2.start();
    }
}

class MyThread extends Thread {
    // 表示线程的名称
    private final String name;

    // 通过构造方法配置name属性
    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "运行, i = " + i);
        }
    }
}


