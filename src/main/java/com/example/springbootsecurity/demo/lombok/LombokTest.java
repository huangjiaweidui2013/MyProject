package com.example.springbootsecurity.demo.lombok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author: huang lang
 * @description: 用于测试 lombok 的一些注解
 * @date: 2022/3/22 11:35
 */
public class LombokTest {
    public static void main(String[] args) {
        StaticClassUtil.publicMethod();

        Map<String, String> map = new HashMap<>();
        map.put("a", "a1");
        map.put("b", "b1");
        map.put("c", "c1");
        map.computeIfAbsent("b", k -> "d1");
        System.out.println(map.get("b"));

        String str = "%";
        System.out.println(str.charAt(0));


    }

}
