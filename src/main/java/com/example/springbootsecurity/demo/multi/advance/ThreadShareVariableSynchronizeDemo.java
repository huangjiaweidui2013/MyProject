package com.example.springbootsecurity.demo.multi.advance;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi.advance
 * @className: ThreadShareVariableSynchronizeDemo
 * @author: HuangLang
 * @description: 线程之间变量有共享与不共享之分，共享理解为大家都使用同一份，不共享理解为每个单独持有一份。   1.共享数据情况
 * @date: 2021-06-28 上午 10:36
 */
public class ThreadShareVariableSynchronizeDemo {
    public static void main(String[] args) {
        Runnable runnable = new ShareVariableRunnable1();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(runnable, "thread:" + (i + 1));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}

class ShareVariableRunnable1 implements Runnable {
    private int count = 10;

    // 解决方案在访问变量方法中增加synchronized关键字
    @Override
    public synchronized void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("" + Thread.currentThread().getName() + ",count:" + count--);
    }
}