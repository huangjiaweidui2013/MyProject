package com.example.springbootsecurity.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: FunctionTest
 * @author: HuangLang
 * @description:
 * @date: 2021-06-15 上午 9:04
 */
public class FunctionTest<T, R> {
//    private Function<T, R> processor = new Function<T, R>() {
//        @Override
//        public R apply(T t) {
//            return (R) new String("apply: " + t);
//        }
//    };

    // lambda表达式的写法:
    private Function<T, R> processor = t -> (R) new String("apply: " + t);

    public static void main(String[] args) {
        FunctionTest<String, String> functionTest = new FunctionTest<>();
        System.out.println(functionTest.processor.apply("==hello=="));

        Function<Integer, Integer> twice = i -> i * 2;
        Function<Integer, Integer> square = i -> i * i;
        Integer apply1 = twice.andThen(square).apply(3);
        //36
        System.out.println("andThen result: " + apply1);
        Integer apply2 = twice.compose(square).apply(3);
        //18
        System.out.println("compose result: " + apply2);
        Function<Integer, Integer> identity = Function.identity();

        String text = String.format("订单%s元", 100);
        String format1 = MessageFormat.format("订单价格{0}元", "100");


        System.out.println(format1);

        System.out.println("123hu汉子：HJK".length());
        System.out.println(getListStr("您好！您有一条消息：N人已预定XXXX-XX-XX的园区企业员工福利工作餐晚餐(打包带走)，请准备。".trim(), 10));
    }

    /**
     * 把String按一定长度拆分，返回ListString
     *
     * @param str
     * @param len
     * @return
     */

    public static List<String> getListStr(String str, int len) {
        List<String> listStr = new ArrayList<>();
        int strLen = str.length();
        int start = 0;
        int num = len;
        String temp = null;
        while (true) {
            try {
                if (num >= strLen) {
                    temp = str.substring(start, strLen);
                } else {
                    temp = str.substring(start, num);
                }
            } catch (Exception e) {
                break;
            }
            if (!"".equals(temp)) {
                listStr.add(temp);
            }
            start = num;
            num = num + len;
        }
        return listStr;
    }

}
