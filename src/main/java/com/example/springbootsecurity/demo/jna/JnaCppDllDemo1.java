package com.example.springbootsecurity.demo.jna;

import com.sun.jna.Native;

/**
 * @author: huang lang
 * @description:
 * @date: 2021/12/20 15:27
 */
public class JnaCppDllDemo1 {
    public static JnaCppDllDemo1 demo = Native.loadLibrary("DlTest", JnaCppDllDemo1.class);

    public native int showInt();

    public native String showName(int age, int id, String name);

    public static void main(String[] args) {

        int i = JnaCppDllDemo1.demo.showInt();
        System.out.println(i);
        String name = JnaCppDllDemo1.demo.showName(18, 100, "studentName");
        System.out.println(name);
    }
}
