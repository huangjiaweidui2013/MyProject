package com.example.springbootsecurity.demo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: LombokBuilderTest
 * @author: HuangLang
 * @description: 测试lombok @builder注解
 * @date: 2021-06-23 下午 2:00
 */
public class LombokBuilderTest {
    public static void main(String[] args) {
        Teacher teacher = Teacher.builder()
                .teacherName("罗翔")
                .teacherAge(42)
                .build();
        Son son = Son.builder()
                .age(15)
                .name("son builder")
                .data(teacher)
                .build();
        System.out.println("父子类使用builder注解: " + son.getData());

        Son2 son2 = Son2.builder()
                .data(teacher)
                .name("son2 builder")
                .build();
        System.out.println(son2.getName());
    }

    @Data
    @SuperBuilder
    static class Father<T> {
        private T data;
        private String name;
    }

    @Data
    @SuperBuilder
    static class Son extends Father<Teacher> {
        private Integer age;
    }

    @SuperBuilder
    static class Son2 extends Father<Teacher> {
    }

    @Data
    @Builder
    static class Teacher {
        private String teacherName;
        private Integer teacherAge;
    }

}
