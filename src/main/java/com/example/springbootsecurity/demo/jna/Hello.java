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

        void testStruct(ArrInfo arrInfo);

        void printUser(User.ByValue user);

        void printUserRef(User user);

        void printGrade(Structure.ByValue grade);

        User changeUser(User user);

        @Data
        @Structure.FieldOrder({"name", "height", "weight"})
        public static class User extends Structure {

            public static class UserValue extends User implements Structure.ByValue {

                public UserValue(String name, int height, double weight) {
                    super(name, height, weight);
                }
            }

            public static class UserByReference extends User implements Structure.ByReference {
                public UserByReference(String name, int height, double weight) {
                    super(name, height, weight);
                }
            }

            public User(String name, int height, double weight) {
                this.name = name;
                this.height = height;
                this.weight = weight;
            }

            public User() {
            }

            public String name;

            public int height;

            public double weight;
        }


        @Data
        @Structure.FieldOrder({"vals", "len"})
        public static class ArrInfo extends Structure {
            public Pointer vals;
            public int len;

            public ArrInfo(Pointer vals, int len) {
                this.vals = vals;
                this.len = len;
            }
        }

        @Data
        @Structure.FieldOrder({"user", "age"})
        public static class Grade extends Structure {
            public static class GradeByValue extends Grade implements Structure.ByValue {
                public GradeByValue(User user, int age) {
                    super(user, age);
                }
            }

            public static class GradeByReference extends Grade implements Structure.ByReference {
                public GradeByReference(User user, int age) {
                    super(user, age);
                }
            }

            public Grade(User user, int age) {
                this.user = user;
                this.age = age;
            }

            public User user;
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
        JnaLibrary.User.UserValue user1 = new JnaLibrary.User.UserValue("user1", 186, 65.2);
        JnaLibrary.INSTANCE.printUserRef(user1);
        JnaLibrary.INSTANCE.printUser(user1);
        // out:
//        printUserRef user: user1 height: 186 weight: 65.20
//        printUser user: user1 height: 186 weight: 65.20
    }

    public static void testGrade() {
//        JnaLibrary.User.UserValue user1 = new JnaLibrary.User.UserValue("user222", 176, 62.4);
//        JnaLibrary.Grade.GradeValue gradeValue = new JnaLibrary.Grade.GradeValue(user1, 18);
//        JnaLibrary.INSTANCE.printGrade(gradeValue);

        JnaLibrary.User.UserValue userValue = new JnaLibrary.User.UserValue("Xiaomi", 182, 55.7);
        JnaLibrary.Grade.ByValue grade = new JnaLibrary.Grade.GradeByValue(userValue, 18);
        JnaLibrary.INSTANCE.printGrade(grade);
    }


    public static void testPointer() {
        // java main test
        int len = 3;
        int shortSize = Native.getNativeSize(Short.class);
        Pointer pointer = new Memory(len * shortSize);
        for (int i = 0; i < len; i++) {
            pointer.setShort(shortSize * i, (short) i);
        }
        JnaLibrary.ArrInfo arrInfo = new JnaLibrary.ArrInfo(pointer, len);
        JnaLibrary.INSTANCE.testStruct(arrInfo);
// out
//        arrInfo[0]: 0
//        arrInfo[1]: 1
//        arrInfo[2]: 2
    }

    public static void testChangeUser() {
        JnaLibrary.User.UserValue user1 = new JnaLibrary.User.UserValue("user1", 186, 65.2);
        JnaLibrary.User user = JnaLibrary.INSTANCE.changeUser(user1);
        System.out.println(user);
    }
}
