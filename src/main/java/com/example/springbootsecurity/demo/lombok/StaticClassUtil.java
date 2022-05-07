package com.example.springbootsecurity.demo.lombok;

import lombok.experimental.UtilityClass;

/**
 * @author localuser
 * create at 2022/3/22 11:36
 * @description
 */
@UtilityClass
public class StaticClassUtil {
    private static String AUTHOR_USER_NAME = "Kitty";

    private String privateMethod() {
        return AUTHOR_USER_NAME;
    }

    void publicMethod() {
        System.out.println("public method");
    }

}
