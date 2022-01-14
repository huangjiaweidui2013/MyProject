package com.example.springbootsecurity.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileInputStream;


/**
 * @author: huang lang
 * @description:
 * @date: 2022/1/12 17:53
 */
class HelloControllerTest extends BaseCommonTest {

    @Test
    void hello() throws Exception {
        File file = new File("D:\\图纸\\图纸\\Circle\\Dwg2013\\Circle2_0.dwg");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        MockMultipartFile fourthFile = new MockMultipartFile("file", "", MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(firstFile)
                        .file(secondFile).file(jsonFile)
                        .param("some-random", "4"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("success"));

    }

    @Test
    void ignoreTest() {
    }

    @Test
    void encode() {
    }

    @Test
    void getConfig() {
    }

    @Test
    void getProperty() {
    }

    @Test
    void getStaticConfig() {
    }

    @Test
    void validStudent() {
    }
}