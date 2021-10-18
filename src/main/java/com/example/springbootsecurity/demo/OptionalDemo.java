package com.example.springbootsecurity.demo;

import java.util.Optional;
import java.util.Random;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: OptionalDemo
 * @author: HuangLang
 * @description: 学习 optional 用法
 * @date: 2021-07-05 下午 5:08
 */
public class OptionalDemo {
    public static void main(String[] args) {
        String[] names = new String[]{"zhang", "wang", "li", "zhao", "wu"};
        String s = Optional.of("hu").orElse(getRandomName(names));
        System.out.println("s: " + s);
        String s1 = Optional.of("wang").orElseGet(() -> getRandomName(names));
        System.out.println("s1: " + s1);

    }

    public static String getRandomName(String[] names) {
        Random random = new Random();
        String res = names[random.nextInt(5)];
        System.out.println("随机返回一个字符串 " + res);
        return res;
    }
}
