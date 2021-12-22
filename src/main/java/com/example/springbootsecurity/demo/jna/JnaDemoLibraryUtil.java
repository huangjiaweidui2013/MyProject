package com.example.springbootsecurity.demo.jna;

import com.example.springbootsecurity.demo.jna.dll.JnaDemoLibrary;
import com.sun.jna.*;

/**
 * @author: huang lang
 * @description:
 * @date: 2021/12/20 15:41
 */
public class JnaDemoLibraryUtil {

    public static void main(String[] args) {
//        testBool();
//        testMax();
//        testAdd();
//        testArray();
//        testUser();
//        testGrade();
//        testPointer();
//        testChangeUser();
        testChangeUser("xiao hua", 17, 54.6);
    }

    /**
     * 测试布尔传值
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/21 20:13
     * @description:
     */
    public static void testBool() {
        JnaDemoLibrary.INSTANCE.getBool(true);
    }

    /**
     * 测试简单传值
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/21 20:18
     * @description:
     */
    public static void testMax() {
        int max = JnaDemoLibrary.INSTANCE.max(100, 200);
        // out: 200
        System.out.println(max);
    }

    /**
     * 测试简单传值
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/21 20:19
     * @description:
     */
    public static void testAdd() {
        System.out.println(JnaDemoLibrary.INSTANCE.add(11, 19));
    }

    /**
     * 测试数组传值
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/21 20:19
     * @description:
     */
    public static void testArray() {
        JnaDemoLibrary.INSTANCE.testArray(new short[]{1, 2, 3, 4}, 4);
    }

    /**
     * 测试结构体传值
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/21 20:20
     * @description:
     */
    public static void testUser() {
        JnaDemoLibrary.User.UserValue user1 = new JnaDemoLibrary.User.UserValue("user1", 186, 65.2);
        JnaDemoLibrary.INSTANCE.printUserRef(user1);
        JnaDemoLibrary.INSTANCE.printUser(user1);
    }

    /**
     * 测试嵌套结构体传值
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/21 20:20
     * @description:
     */
    public static void testGrade() {
        JnaDemoLibrary.User.UserValue userValue = new JnaDemoLibrary.User.UserValue("Xiaomi", 182, 55.7);
        JnaDemoLibrary.Grade.ByValue grade = new JnaDemoLibrary.Grade.GradeByValue(userValue, 18);
        JnaDemoLibrary.INSTANCE.printGrade(grade);
    }


    public static void testPointer() {
        int len = 3;
        int shortSize = Native.getNativeSize(Short.class);
        Pointer pointer = new Memory(len * shortSize);
        for (int i = 0; i < len; i++) {
            pointer.setShort(shortSize * i, (short) i);
        }
        JnaDemoLibrary.ArrInfo arrInfo = new JnaDemoLibrary.ArrInfo(pointer, len);
        JnaDemoLibrary.INSTANCE.testStruct(arrInfo);
    }

    public static void testChangeUser() {
        JnaDemoLibrary.User.UserValue user1 = new JnaDemoLibrary.User.UserValue("user1", 186, 65.2);
        JnaDemoLibrary.User user = JnaDemoLibrary.INSTANCE.changeUser(user1);
        System.out.println(user);
    }

    public static void testChangeUser(String name, int height, double weight) {
        JnaDemoLibrary.User.UserValue user1 = new JnaDemoLibrary.User.UserValue(name, height, weight);
        JnaDemoLibrary.User user = JnaDemoLibrary.INSTANCE.changeUser(user1);
        System.out.println(user);
    }
}
