package com.example.springbootsecurity.service;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.service
 * @className: MyTestService
 * @author: HuangLang
 * @description: 测试用的service
 * @date: 2021-07-13 下午 4:23
 */
public interface MyTestService {
    String getLocalDateTime();

    void ThreadPrintInfo();

    void ThreadPrintRandom();
}
