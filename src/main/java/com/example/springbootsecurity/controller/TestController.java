package com.example.springbootsecurity.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
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
    public static void main(String[] args) {
//        StringBuilder stringBuilder1 = new StringBuilder("");
//        StringBuilder stringBuilder2 = new StringBuilder();
//        System.out.println(stringBuilder1);
//        System.out.println(stringBuilder2);
//
//        long currentTimeMillis = System.currentTimeMillis();
//        System.out.println(currentTimeMillis);

        List<Long> longList = new ArrayList<>();
        longList.add(1356L);
        longList.add(135556L);
        Long[] longs = Convert.toLongArray(longList);
        Arrays.stream(longs).forEach(System.out::println);

//        String s = addWithSuffix("my.test.file.dwg", ".", "-copy");
//        String s2 = addWithSuffix("my-test./hh.file////ll.dwg", ".", "-copy");
//        System.out.println(s2);


//        jsonToString();
//        System.out.println(null == null);
//        System.out.println(Objects.equals(null, null));
//        System.out.println(Objects.equals(null, 100L));
//        System.out.println(Objects.deepEquals(new Integer[]{1, 2, 3}, new Integer[]{1, 2, 3, 4}));
//
//        StringEscapeUtils.escapeJson("");
//
//        System.out.println(null instanceof String);
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
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return between_days;
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
        root.put("data", dataNode);
        System.out.println(root);
    }

    private static String addWithSuffix(String name, String separator, String suffix) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        int index = name.lastIndexOf(separator);
        String s1 = name.substring(0, index);
        String s2 = name.substring(index);
        String s = s1 + suffix + s2;
        return s;
    }
}
