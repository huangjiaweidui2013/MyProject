package com.example.springbootsecurity.test;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: huang lang
 * @description: 杂项测试代码编写
 * @date: 2022/1/4 16:40
 */
public class OtherTest {
    public static void main(String[] args) throws IOException {
        String path = "./abc/huawei/test.txt";
        String normalize = FilenameUtils.normalize(path);
        String name = FilenameUtils.getName(normalize);
        System.out.println(normalize);
        System.out.println(name);

    }


    public static void md5() throws IOException {
        FileInputStream fileInputStream0 = new FileInputStream("D:\\java开发工具\\sogou_pinyin_114a.exe");
//        FileInputStream fileInputStream0 = new FileInputStream("D:\\java_tools.rar");
        long start = System.currentTimeMillis();
        String s0 = DigestUtils.md5DigestAsHex(fileInputStream0);
        long end = System.currentTimeMillis();
        System.out.println("s0: " + s0 + " , 耗时: " + (end - start) + " ms");
    }
}
