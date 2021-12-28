package com.example.springbootsecurity.demo.jna;

import com.sun.jna.*;
import lombok.Data;

/**
 * @author: huang lang
 * @description:
 * @date: 2021/12/20 15:41
 */
public class Hello {
    public interface JnaLibrary extends Library {

        // JNA 为 dll 名称
        JnaLibrary INSTANCE = Native.load("JNA", JnaLibrary.class);

        int max(int a, int b);

        int add(int a, int b);

        void getBool(boolean x);

        void testArray(short[] vals, int len);

        void testStruct(MyArrInfo myArrInfo);

        void printUser(MyUser.ByValue user);

        void printUserRef(MyUser user);

        void printGrade(Structure.ByValue grade);

        MyUser changeUser(MyUser user);

        @Data
        @Structure.FieldOrder({"name", "height", "weight"})
        public static class MyUser extends Structure {

            public static class UserValue extends MyUser implements Structure.ByValue {

                public UserValue(String name, int height, double weight) {
                    super(name, height, weight);
                }
            }

            public static class UserByReference extends MyUser implements Structure.ByReference {
                public UserByReference(String name, int height, double weight) {
                    super(name, height, weight);
                }
            }

            public MyUser(String name, int height, double weight) {
                this.name = name;
                this.height = height;
                this.weight = weight;
            }

            public MyUser() {
            }

            public String name;

            public int height;

            public double weight;
        }


        @Data
        @Structure.FieldOrder({"vals", "len"})
        public static class MyArrInfo extends Structure {
            public Pointer vals;
            public int len;

            public MyArrInfo(Pointer vals, int len) {
                this.vals = vals;
                this.len = len;
            }
        }

        @Data
        @Structure.FieldOrder({"user", "age"})
        public static class Grade extends Structure {
            public static class GradeByValue extends Grade implements Structure.ByValue {
                public GradeByValue(MyUser user, int age) {
                    super(user, age);
                }
            }

            public static class GradeByReference extends Grade implements Structure.ByReference {
                public GradeByReference(MyUser user, int age) {
                    super(user, age);
                }
            }

            public Grade(MyUser user, int age) {
                this.user = user;
                this.age = age;
            }

            public MyUser user;
            public int age;
        }

    }

    public static void main(String[] args) {
//        testBool();
//        testMax();
//        testAdd();
//        testArray();
//        testUser();
//        testGrade();
//        System.out.println("testGrade: " + testGrade());
//        testPointer();
        testChangeUser();
    }

    public static void testBool() {
        // c++ output:
        // bool: 255 in true
        JnaLibrary.INSTANCE.getBool(true);
    }

    public static void testMax() {
        int max = JnaLibrary.INSTANCE.max(100, 200);
        // out: 200
        System.out.println(max);
    }

    public static void testAdd() {
        System.out.println(JnaLibrary.INSTANCE.add(11, 19));
    }

    public static void testArray() {
        JnaLibrary.INSTANCE.testArray(new short[]{1, 2, 3, 4}, 4);
    }

    public static void testUser() {
        JnaLibrary.MyUser.UserValue user1 = new JnaLibrary.MyUser.UserValue("user1", 186, 65.2);
        JnaLibrary.INSTANCE.printUserRef(user1);
        JnaLibrary.INSTANCE.printUser(user1);
        // out:
//        printUserRef user: user1 height: 186 weight: 65.20
//        printUser user: user1 height: 186 weight: 65.20
    }

    public static String testGrade() {
//        JnaLibrary.User.UserValue user1 = new JnaLibrary.User.UserValue("user222", 176, 62.4);
//        JnaLibrary.Grade.GradeValue gradeValue = new JnaLibrary.Grade.GradeValue(user1, 18);
//        JnaLibrary.INSTANCE.printGrade(gradeValue);

        JnaLibrary.MyUser.UserValue userValue = new JnaLibrary.MyUser.UserValue("Xiaomi", 182, 55.7);
        JnaLibrary.Grade.ByValue grade = new JnaLibrary.Grade.GradeByValue(userValue, 18);
        JnaLibrary.INSTANCE.printGrade(grade);
        return grade.toString();
    }


    public static void testPointer() {
        // java main test
        int len = 3;
        int shortSize = Native.getNativeSize(Short.class);
        Pointer pointer = new Memory(len * shortSize);
        for (int i = 0; i < len; i++) {
            pointer.setShort(shortSize * i, (short) i);
        }
        JnaLibrary.MyArrInfo myArrInfo = new JnaLibrary.MyArrInfo(pointer, len);
        JnaLibrary.INSTANCE.testStruct(myArrInfo);
// out
//        arrInfo[0]: 0
//        arrInfo[1]: 1
//        arrInfo[2]: 2
    }

    public static void testChangeUser() {

        System.setProperty("jna.encoding", "GB2312");
        JnaLibrary.MyUser.UserValue user1 = new JnaLibrary.MyUser.UserValue("user1", 186, 65.2);
        JnaLibrary.MyUser user = JnaLibrary.INSTANCE.changeUser(user1);
        System.out.println(user);
    }
}
