package com.example.springbootsecurity.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: huang lang
 * @description: person 对象
 * @date: 2022/3/18 16:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long id;
    private Integer age;
    private String name;
}
