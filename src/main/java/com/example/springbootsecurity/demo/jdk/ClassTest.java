package com.example.springbootsecurity.demo.jdk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: huang lang
 * @description: 测试 jdk 中的一些方法
 * @date: 2022/2/25 14:18
 */
public class ClassTest {
    public static void main(String[] args) {
        Class<Map> mapClass = Map.class;
        Class<HashMap> hashMapClass = HashMap.class;
        Class<List> listClass = List.class;
        boolean b1 = mapClass.isAssignableFrom(hashMapClass);
        System.out.println("mapClass.isAssignableFrom(hashMapClass): " + b1);
        boolean b2 = mapClass.isAssignableFrom(listClass);
        System.out.println("mapClass.isAssignableFrom(listClass): " + b2);
        boolean b3 = hashMapClass.isAssignableFrom(mapClass);
        System.out.println("hashMapClass.isAssignableFrom(mapClass): " + b3);
    }
}
