package com.example.springbootsecurity.controller;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: TestController
 * @author: HuangLang
 * @description: test
 * @date: 2021-06-10 下午 6:05
 */
@Slf4j
public class TestController {

    public static void main(String[] args) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        removeMapKey(map, "b");
        map.forEach((k, v) ->
                System.out.println("K: " + k + ", V: " + v)
        );


    }


    private static void removeMapKey(Map<String, Integer> map, String key) {
        map.remove(key);
    }

    private static void md5Hex() throws IOException {

//        FileInputStream fileInputStream0 = new FileInputStream("D:\\java_tools.rar");
        FileInputStream fileInputStream0 = new FileInputStream("D:\\java开发工具\\sogou_pinyin_114a.exe");
        long start = System.currentTimeMillis();
        String s0 = DigestUtils.md5Hex(fileInputStream0);
        long end = System.currentTimeMillis();
        System.out.println("s0: " + s0 + " , 耗时: " + (end - start) + " ms");

//
//        FileInputStream fileInputStream00 = new FileInputStream("D:\\java开发工具\\sogou_pinyin_114a.exe");
//        long start1 = System.currentTimeMillis();
//        String sha256Hex = DigestUtils.sha256Hex(fileInputStream00);
//        long end1 = System.currentTimeMillis();
//        System.out.println("sha256Hex: " + sha256Hex + " , 耗时: " + (end1 - start1) + " ms");
//
//
//        FileInputStream fileInputStream1 = new FileInputStream("D:\\jna_test\\测试文档名称.md");
//        String s1 = DigestUtils.sha256Hex(fileInputStream1);
//
//        FileInputStream fileInputStream2 = new FileInputStream("D:\\jna_test\\test\\测试文档名称.md");
//        String s2 = DigestUtils.sha256Hex(fileInputStream2);
//        System.out.println("s1: " + s1 + " , s2: " + s2 + " , 是否相同: " + s1.equals(s2));


//        Person p1 = Person.builder().id(100L).age(18).name("Jim").build();
//        Person p2 = Person.builder().id(101L).age(19).name("Kitty").build();
//        Person p3 = Person.builder().id(102L).age(20).name("Kate").build();
//        List<Person> dishList = CollUtil.newArrayList(p1, p2, p3);
//        List<Person> dishList2 = CollUtil.newArrayList();
//        BeanUtils.copyProperties(dishList, dishList2);
//        List<Person> people = BeanUtil.copyToList(dishList, Person.class);
//        people.forEach(Person -> {
//            System.out.println(Person.toString());
//        });
//
//        String s = IdUtil.objectId();
//        String fastSimpleUUID = IdUtil.fastSimpleUUID();
//        System.out.println(s);
//        System.out.println(fastSimpleUUID);
    }


    private static void putComputeResultMap(Map<String, Object> map, Long id, Object couponUser,
                                            Long couponDiscountAmount) {
        map.put("ID", id);
        map.put("COUPON_USER", couponUser);
        map.put("COUPON_DISCOUNT_AMOUNT", couponDiscountAmount);
    }


    private static Long daysBetween(Date date1, Date date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date1 = sdf.parse(sdf.format(date1));
        date2 = sdf.parse(sdf.format(date2));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        return (time2 - time1) / (1000 * 3600 * 24);
    }

    private static void jsonToString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("id", IdUtil.objectId());
        root.put("function", "opendoc");
        ArrayNode arrayNode1 = mapper.createArrayNode();
        arrayNode1.add(0);
        arrayNode1.add(1);
        arrayNode1.add(2);
        root.set("extMin", arrayNode1);
        root.set("extMax", arrayNode1);
        ObjectNode dataNode = mapper.createObjectNode();
        dataNode.put("docId", IdUtil.objectId());
        dataNode.put("dwgpath", "/data/upload");
        root.set("data", dataNode);
        System.out.println(root);
    }

    private static String addWithSuffix(String name, String separator, String suffix) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        int index = name.lastIndexOf(separator);
        String s1 = name.substring(0, index);
        String s2 = name.substring(index);
        return s1 + suffix + s2;
    }

    private static void strJoin() {
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        List<Integer> list = List.of(100, 200, 300, 400, 500);
        list.forEach(t -> stringJoiner.add(t.toString()));
        System.out.println(stringJoiner.toString());
    }
}
