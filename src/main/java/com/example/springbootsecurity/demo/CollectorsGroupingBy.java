package com.example.springbootsecurity.demo;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.demo
 * @className: CollectorsGroupingBy
 * @author: HuangLang
 * @description: 学习 collectors 的 grouping by 用法
 * @date: 2021-06-25 下午 4:43
 */
public class CollectorsGroupingBy {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        List<Apple> list = appleList();

        try {
//            groupByConcatField(list);
//            groupByCustom(list);
//            groupByMulti(list);
//            groupByAndCount(list);
            groupByAndSum(list);
            groupByAndSummarize(list);
//            groupByAndMaxBy(list);
//            groupBySet(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        List<Apple> collect = sortByMultipleProperties(list);
//        groupBySingleField(collect);
//        sortByMultiplePropertiesAndGroupBy(list);
    }

    /**
     * list 按照多个属性排序
     *
     * @param list
     */
    static List<Apple> sortByMultipleProperties(List<Apple> list) {
        List<Apple> collect =
                list.stream().sorted(Comparator.comparing(Apple::getNum).reversed().thenComparing(Apple::getName).thenComparing(Apple::getPrice).reversed()).collect(Collectors.toList());
        collect.forEach(System.out::println);
        return collect;
    }

    static void sortByMultiplePropertiesAndGroupBy(List<Apple> list) {
        Map<String, Map<Integer, List<Apple>>> collect =
                list.stream().sorted(Comparator.comparing(Apple::getNum).reversed().thenComparing(Apple::getName).thenComparing(Apple::getPrice).reversed()).collect(Collectors.groupingBy(Apple::getCategory, Collectors.groupingBy(Apple::getNum)));
        collect.forEach((k, v) -> {
            System.out.println(k);
            v.forEach((k1, v1) -> {
                System.out.println(k1);
                v1.forEach(System.out::println);
            });
        });
    }

    /**
     * 按照单个属性来分组
     */
    static void groupBySingleField(List<Apple> list) {
        // 按照类目分组
        Map<String, List<Apple>> listMap = list.stream().collect(Collectors.groupingBy(Apple::getCategory));
        listMap.forEach(
                (k, v) ->
                {
                    System.out.println(k);
                    v.forEach(System.out::println);
                }
        );
    }

    /**
     * 按照几个属性拼接分组
     */
    static void groupByConcatField(List<Apple> list) throws JsonProcessingException {
        // 按照类目分组
        Map<String, List<Apple>> listMap =
                list.stream().collect(Collectors.groupingBy(obj -> obj.getCategory() + "_" + obj.getName()));
        System.out.println(mapper.writeValueAsString(listMap));
    }

    /**
     * 根据不同条件分组
     */
    static void groupByCustom(List<Apple> list) throws JsonProcessingException {
        // 按照类目分组
        Map<String, List<Apple>> listMap =
                list.stream().collect(Collectors.groupingBy(obj -> {
                    if (obj.getNum() < 3) {
                        return "小于3";
                    } else {
                        return "大于等于3";
                    }
                }));
        System.out.println(mapper.writeValueAsString(listMap));
    }

    /**
     * 多级分组
     */
    static void groupByMulti(List<Apple> list) throws JsonProcessingException {
        Map<String, Map<String, List<Apple>>> listMap =
                list.stream().collect(Collectors.groupingBy(Apple::getCategory,
                        Collectors.groupingBy(obj -> {
                            if (obj.getNum() < 3) {
                                return "小于3";
                            } else {
                                return "大于等于3";
                            }
                        })));
        System.out.println(mapper.writeValueAsString(listMap));
    }

    //---------------------按子组收集数据--------------------//

    /**
     * 计数
     */
    static void groupByAndCount(List<Apple> list) throws JsonProcessingException {
        Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(Apple::getCategory,
                Collectors.counting()));
        System.out.println(mapper.writeValueAsString(collect));
    }

    /**
     * 求和
     */
    static void groupByAndSum(List<Apple> list) throws JsonProcessingException {
        Map<String, Integer> collect = list.stream().collect(Collectors.groupingBy(Apple::getCategory,
                Collectors.summingInt(Apple::getNum)));
        System.out.println(mapper.writeValueAsString(collect));
    }

    /**
     * 把收集器的结果转换为另一种类型: 按照类目分组，然后取出其中num最大的对象
     */
    static void groupByAndMaxBy(List<Apple> list) throws JsonProcessingException {
        Map<String, Apple> collect = list.stream().collect(Collectors.toMap(Apple::getCategory, Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Apple::getNum))));
        System.out.println(mapper.writeValueAsString(collect));
    }

    /**
     * 联合其他收集器
     */
    static void groupBySet(List<Apple> list) throws JsonProcessingException {
        Map<String, Set<String>> collect = list.stream().collect(Collectors.groupingBy(Apple::getCategory,
                Collectors.mapping(Apple::getName, Collectors.toSet())));
        System.out.println(mapper.writeValueAsString(collect));
    }

    /**
     * 分组汇总
     *
     * @param list
     * @throws JsonProcessingException
     */
    static void groupByAndSummarize(List<Apple> list) throws JsonProcessingException {
        Map<String, IntSummaryStatistics> collect = list.stream().collect(Collectors.groupingBy(Apple::getCategory,
                Collectors.summarizingInt(Apple::getNum)));
        System.out.println(mapper.writeValueAsString(collect));
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static
    class Apple {
        private Long id;
        private Integer num;
        private BigDecimal price;
        private String name;
        /**
         * 类目
         */
        private String category;
    }

    static List<Apple> appleList() {
        Apple apple1 = Apple.builder().id(1L).num(1).price(new BigDecimal("15.5")).name("绿苹果1").category("绿色").build();
        Apple apple2 = Apple.builder().id(2L).num(2).price(new BigDecimal("10.5")).name("绿苹果2").category("绿色").build();
        Apple apple3 = Apple.builder().id(3L).num(3).price(new BigDecimal("15.5")).name("绿苹果3").category("绿色").build();
        Apple apple4 = Apple.builder().id(4L).num(3).price(new BigDecimal("20")).name("红苹果1").category("红色").build();
        Apple apple5 = Apple.builder().id(5L).num(5).price(new BigDecimal("30")).name("红苹果2").category("红色").build();
        Apple apple6 = Apple.builder().id(6L).num(5).price(new BigDecimal("10")).name("红苹果3").category("红色").build();
        Apple apple7 = Apple.builder().id(7L).num(2).price(new BigDecimal("20")).name("绿苹果2").category("绿色").build();
        Apple apple8 = Apple.builder().id(8L).num(2).price(new BigDecimal("15")).name("绿苹果3").category("绿色").build();
        Apple apple9 = Apple.builder().id(9L).num(2).price(new BigDecimal("10")).name("绿苹果3").category("绿色").build();
        Apple apple10 = Apple.builder().id(10L).num(3).price(new BigDecimal("15")).name("红苹果1").category("红色").build();
        return ListUtil.list(false, apple1, apple2, apple3, apple4, apple5, apple6, apple7, apple8, apple9, apple10);
    }
}
