package com.example.springbootsecurity.controller;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
        System.out.println(RandomUtil.randomInt(0, 20));
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("k1", "v1");
//        map1.put("k2", "v2");
//        Map<String, String> map2 = new HashMap<>();
//        map2.put("k2", "v31");
//        map2.put("k3", "v3");
//        Map<String, String> map3 = new HashMap<>();
//        map3.put("k4", "v4");
//        map3.put("k5", "v5");
//
//        List<Map<String, String>> list = Stream.of(map1, map2, map3).collect(Collectors.toList());
//
//        Map<String, String> map = list.stream()
//                .map(Map::entrySet)
//                .flatMap(Set::stream)
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k1, k2) -> k2));
//
//        System.out.println(map);

        log.info("==========================");
//
//        Long a1 = 10L;
//        float f1 = Float.parseFloat(a1.toString());
//        float f2 = Float.parseFloat("100");
//        float r = f1 / f2;
//        String format = MessageFormat.format("订单金额{0}元", f1 / f2);
//        System.out.println(format);
//
//        StringBuilder stringBuilder = new StringBuilder("");
//        System.out.println("长度: " + stringBuilder.length());


//        String s = String.format("%.2f", (double) (10L / 100));
//        System.out.println(s);
//        try {
//            File file = new File("D:\\templates113\\view\\index.txt");
//            FileInputStream fis = new FileInputStream(file);
//            System.out.println(file.getName());
//        } catch (Exception e) {
//            log.error("测试捕捉异常并打印错误信息{},额外的信息{}", e.getMessage(), "===========", e);
//        }

//        String str = "103";
//        String[] strings = new String[]{"104", "108", "456"};
//        boolean contains = Arrays.asList(strings).contains("104");

//        boolean supportFirst =
//                Arrays.asList(str.split(",")).contains(String.valueOf("103"));
//        System.out.println(supportFirst);
//        Map<String, Object> ajax = new HashMap<>();
//        Integer code = 0;
//        String message = "ceshi";
//        ajax.put("code", code);
//        ajax.put("message", message);
//
//        code = 1;
//        message = "jaiaia";
//        System.out.println(ajax.get("message"));

//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            Map<String, Object> map = new HashMap<>();
//            map.put("content", "您好！您有一条申请审核已处理：5人招待餐中餐自助餐于2021-07-29用餐的申请已通过");
//            String s = mapper.writeValueAsString(map);
//            System.out.println(s);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//        Long a = null;
//        System.out.println(String.valueOf(a));
//
//        List<String> list = Arrays.asList("11011", "1101", "0110");
//        if (list.contains("11")) {
//            System.out.println("是");
//        } else {
//            System.out.println("否");
//        }

//        List<String> list = Lists.newArrayList();
//        list.add("asd");
//        list.add("ssasd");
//        list.add("sassd");
//        list.add("sasd");
//        list.add("sasd2");
//        list.removeIf(t -> t.startsWith("sa"));
//        list.forEach(System.out::println);
//
//        Date now = new Date();
//        System.out.println(now);
//        Map<String, Object> map = new HashMap<>();
//        putComputeResultMap(map, 10L, null, 10000L);
//        map.forEach((k, v) -> {
//            System.out.println(k + " -> " + v);
//        });

//        System.out.println("123".compareTo("123456788"));
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date d1 = sdf.parse("2012-09-08 10:10:10");
//            Date d2 = sdf.parse("2012-09-09 15:00:00");
//            System.out.println(daysBetween(d1, d2));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        System.out.println(null == null);
        System.out.println(Objects.equals(null, null));
        System.out.println(Objects.equals(null, 100L));
        System.out.println(Objects.deepEquals(new Integer[]{1, 2, 3}, new Integer[]{1, 2, 3, 4}));
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
}
