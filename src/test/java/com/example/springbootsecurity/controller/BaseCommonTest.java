package com.example.springbootsecurity.controller;

import com.example.springbootsecurity.SpringBootSecurityApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author: huang lang
 * @description:
 * @date: 2022/1/12 17:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootSecurityApplication.class)
@AutoConfigureMockMvc
public class BaseCommonTest {
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }
}
