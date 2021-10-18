package com.example.springbootsecurity.demo.multi.advance;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo.multi.advance
 * @className: ThreadInterruptDemo2
 * @author: HuangLang
 * @description: 使用interrupt方法中断线程
 * @date: 2021-06-28 上午 11:48
 */
public class ThreadInterruptDemo2 {
    public volatile static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
//        正确的线程中止 - interrupt
//        Thread thread = new InterruptThread("thread_1");
//        thread.start();
//        Thread.sleep(20);
//        System.out.println(thread.getName() + "线程设置:interrupt");
//        //正确的线程中止 - interrupt
//        thread.interrupt();

        //正确的线程中止 - 标志位
        new Thread(() -> {
            try {
                while (flag) {
                    System.out.println(Thread.currentThread().getName() + "线程运行中");
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "test线程").start();
        Thread.sleep(3000L);
        flag = false;
        System.out.println("程序运行结束");
    }
}

class InterruptThread extends Thread {

    public InterruptThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(Thread.currentThread().getName() + "线程开始!");
        try {
            for (int i = 0; i < 1000; i++) {
                if (Thread.interrupted()) {
                    System.out.println(threadName + "线程停止状态!");
                    throw new InterruptedException();
                }
                Thread.sleep(0);
                System.out.println("" + (i + 1));
            }
            System.out.println(threadName + "线程结束!");
        } catch (InterruptedException e) {
            System.out.println(threadName + "线程捕获异常,退出循环!");
            e.printStackTrace();
        }
    }

}