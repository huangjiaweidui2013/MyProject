package com.example.springbootsecurity.demo.jna;

/**
 * @author: huang lang
 * @description: 测试使用JNA开源框架调用C++动态链接库
 * @date: 2021/12/20 11:54
 */
public class JnaCppDllDemo2 {
    static {
        System.loadLibrary("DlTest");
    }

    public native int showInt();

    public native String showName(int age, int id, String name);

    public static void main(String[] args) {
        System.out.println(new JnaCppDllDemo2().showInt());
        System.out.println(new JnaCppDllDemo2().showName(18, 100, "studentName"));
    }
}
