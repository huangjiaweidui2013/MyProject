package com.example.springbootsecurity.demo.jna.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.Data;

/**
 * @author: huang lang
 * @description:
 * @date: 2021/12/21 18:59
 */
public interface JnaDemoLibrary extends Library {
    /**
     * JNA 为 c++ 动态链接库 名称
     */
    JnaDemoLibrary INSTANCE = Native.load("JNA", JnaDemoLibrary.class);

    /**
     * c++ 动态链接库中的方法
     */
    int max(int a, int b);

    int add(int a, int b);

    void getBool(boolean x);

    void testArray(short[] vals, int len);

    void testStruct(ArrInfo arrInfo);

    void printUser(User.ByValue user);

    void printUserRef(User user);

    void printGrade(Grade.ByValue grade);

    JnaDemoLibrary.User changeUser(JnaDemoLibrary.User user);

    /**
     * 请求参数封装，模仿 C++ 结构体
     */
    @Data
    @Structure.FieldOrder({"name", "height", "weight"})
    class User extends Structure {

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
    class ArrInfo extends Structure {
        public Pointer vals;
        public int len;

        public ArrInfo(Pointer vals, int len) {
            this.vals = vals;
            this.len = len;
        }
    }

    @Data
    @Structure.FieldOrder({"user", "age"})
    class Grade extends Structure {
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
