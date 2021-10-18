package com.example.springbootsecurity.demo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: MapSort
 * @author: HuangLang
 * @description: java map排序
 * @date: 2021-07-30 上午 10:48
 */
public class MapSort {
    public static void main(String[] args) {
        Map<String, Integer> unsortedMap = new HashMap<>();
        unsortedMap.put("z", 10);
        unsortedMap.put("b", 5);
        unsortedMap.put("a", 6);
        unsortedMap.put("c", 20);
        unsortedMap.put("d", 1);
        unsortedMap.put("e", 7);
        unsortedMap.put("y", 8);
        unsortedMap.put("n", 99);
        unsortedMap.put("g", 50);
        unsortedMap.put("m", 2);
        unsortedMap.put("f", 9);

        // 传统的Map迭代方式
        for (Map.Entry<String, Integer> entry : unsortedMap.entrySet()) {
            System.out.println("[" + entry.getKey() + " , " + entry.getValue() + "]");
        }

        // JDK8的迭代方式
        unsortedMap.forEach((k, v) -> System.out.println("unsortedMap [" + k + " , " + v + "]"));

        // 1、Java8对map按key排序
        LinkedHashMap<String, Integer> result1 = unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> newValue,
                        LinkedHashMap::new));

        Map<String, Integer> result2 = new LinkedHashMap<>();
        unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));

        TreeMap<String, Integer> result3 =
                unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByKey(String::compareToIgnoreCase)).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> newValue,
                                TreeMap::new));

        // JDK8的迭代方式
        result1.forEach((k, v) -> System.out.println("result1 [" + k + " , " + v + "]"));
        // JDK8的迭代方式
        result2.forEach((k, v) -> System.out.println("result2 [" + k + " , " + v + "]"));
        // JDK8的迭代方式
        result3.forEach((k, v) -> System.out.println("result3 [" + k + " , " + v + "]"));

    }
}
