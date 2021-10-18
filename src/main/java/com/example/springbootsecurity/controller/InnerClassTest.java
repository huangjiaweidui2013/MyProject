package com.example.springbootsecurity.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: InnerClassTest
 * @author: HuangLang
 * @description: 测试内部类
 * @date: 2021-06-21 上午 10:27
 */
public final class InnerClassTest {

    public static InnerClass build(String name) {
        return new InnerClass(name, new ArrayList<>());
    }

    public static class InnerClass {
        private final String name;
        private final List<String> list;

        public InnerClass(String name, List<String> list) {
            this.name = name;
            this.list = list;
        }

        public InnerClass InnerClass(String name) {
            return this;
        }

    }
}
