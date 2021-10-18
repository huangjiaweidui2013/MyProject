package com.example.springbootsecurity.demo;

import cn.hutool.core.collection.ListUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: AnyToMap
 * @author: HuangLang
 * @description: 常见对象转map
 * @date: 2021-06-15 上午 9:44
 */
public class AnyToMap {
    public static void main(String[] args) {
        List<Apple> list = ListUtil.list(false);
        list.add(new Apple(1, "red", new BigDecimal("2.50"), 5));
        list.add(new Apple(2, "green", new BigDecimal("2.50"), 2));
        list.add(new Apple(3, "red", new BigDecimal("2.50"), 7));
        list.add(new Apple(4, "yellow", new BigDecimal("2.50"), 3));
        list.add(new Apple(5, "red", new BigDecimal("2.50"), 5));
        list.add(new Apple(6, "green", new BigDecimal("2.50"), 2));

        //第一种： 取list中某2个字段作为Map的K,V
        Map<Integer, String> collect0 = list.stream().collect(Collectors.toMap(Apple::getId, Apple::getName));

        //第二种：将id和实体Bean做为K,V
        Map<Integer, Apple> collect1 = list.stream().collect(Collectors.toMap(Apple::getId, t -> t));
        Map<Integer, Apple> collect2 = list.stream().collect(Collectors.toMap(Apple::getId, Function.identity()));

        //第三种： key存在重复记录时处理,这里只是简单的使用后者覆盖前者来解决key重复问题
        Map<Integer, Apple> collect3 = list.stream().collect(Collectors.toMap(Apple::getId, t -> t, (v1, v2) -> v2));

        //第四种： 使用某个具体的Map类来保存，如保存时使用LinkedHashMap
        LinkedHashMap<Integer, Apple> collect4 = list.stream().collect(Collectors.toMap(Apple::getId,
                Function.identity(), (v1, v2) -> v2, LinkedHashMap::new));

        ConcurrentMap<Integer, Apple> collect5 = list.stream().collect(Collectors.toConcurrentMap(Apple::getId,
                Function.identity(), (v1, v2) -> v1));

        // list 排序
        List<Apple> collect =
                list.stream().sorted(Comparator.comparingInt(Apple::getId).reversed().thenComparingInt(Apple::getNum))
                        .collect(Collectors.toList());

        List<Apple> collect6 =
                list.stream().sorted(Comparator.comparing(Apple::getName, Comparator.reverseOrder())
                        .thenComparingInt(Apple::getNum).reversed()).collect(Collectors.toList());
        List<Apple> collect7 =
                list.stream().sorted(Comparator.comparing(Apple::getName, Comparator.naturalOrder())
                        .thenComparing(Apple::getNum, Comparator.reverseOrder()).thenComparingInt(Apple::getId)
                        .reversed()).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static
    class Apple {
        private Integer id;
        private String name;
        private BigDecimal money;
        private Integer num;
    }
}
