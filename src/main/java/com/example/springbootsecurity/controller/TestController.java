package com.example.springbootsecurity.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springbootsecurity.demo.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public static void main(String[] args) {
        String proj = "proj";
//        String format = MessageFormat.format("测试{0} == {1} === {2}===={5}", "abc", "def", "ghi", "jkl");
//        System.out.println(format);
//
//        List<Long> list = CollUtil.newArrayList(100L, 200L, 300L);
//        Long[] array = list.toArray(new Long[0]);
//        for (Long aLong : array) {
//            System.out.println(aLong);
//        }


        Person p1 = Person.builder().id(100L).age(18).name("Jim").build();
        Person p2 = Person.builder().id(101L).age(19).name("Kitty").build();
        Person p3 = Person.builder().id(102L).age(20).name("Kate").build();
        List<Person> dishList = CollUtil.newArrayList(p1, p2, p3);
        List<Person> dishList2 = CollUtil.newArrayList();
        BeanUtils.copyProperties(dishList, dishList2);
        List<Person> people = BeanUtil.copyToList(dishList, Person.class);
        people.forEach(Person -> {
            System.out.println(Person.toString());
        });

        String s = IdUtil.objectId();
        String fastSimpleUUID = IdUtil.fastSimpleUUID();
        System.out.println(s);
        System.out.println(fastSimpleUUID);

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
}
