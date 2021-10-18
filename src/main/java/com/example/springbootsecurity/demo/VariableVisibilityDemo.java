package com.example.springbootsecurity.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: VariableVisibilityDemo
 * @author: HuangLang
 * @description: 变量可见性的解决方案
 * @date: 2021-07-07 上午 10:11
 */
public class VariableVisibilityDemo {
    volatile static int start = 3;
    volatile static int end = 6;

    private int number = 0;
    private AtomicInteger number2 = new AtomicInteger();
    private static ReentrantLock lock = new ReentrantLock();

    private int increase() {
        synchronized (this) {
            number++;
        }
        return number;
    }

    private int increase2() {
        lock.lock();
        try {
            number++;
        } finally {
            lock.unlock();
        }
        return number;
    }

    private int increase3() {
        return number2.addAndGet(1);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            while (start < end) {
                System.out.println("start < end : " + start + " " + end);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                start += 3;
                end += 3;
            }
        }).start();
    }

}

/**
 * 线程安全的懒汉式（双重检查加锁）
 */
class Singleton {
    private volatile static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
