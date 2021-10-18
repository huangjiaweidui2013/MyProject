package com.example.springbootsecurity.controller;

import cn.hutool.core.collection.ListUtil;
import com.example.springbootsecurity.domain.BO.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: ThymeleafController
 * @author: HuangLang
 * @description: thymeleaf 模板接口
 * @date: 2021-06-24 上午 9:33
 */
@RestController
public class ThymeleafController {
    @RequestMapping("/index")
    public ModelAndView index() {
        List<Book> list = ListUtil.list(false);
        list.add(Book.builder().id(1).name("java").author("Jack Ma").build());
        list.add(Book.builder().id(2).name("C#").author("Tony Ma").build());
        ModelAndView mv = new ModelAndView();
        mv.addObject("books", list);
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/index2")
    public ModelAndView index2() {
        List<Book> list = ListUtil.list(false);
        list.add(Book.builder().id(1).name("java").author("Jack Ma").build());
        list.add(Book.builder().id(2).name("C#").author("Tony Ma").build());
        ModelAndView mv = new ModelAndView();
        mv.addObject("books", list);
        mv.setViewName("index2");
        return mv;
    }

    @RequestMapping("/index3")
    public ModelAndView index3() {
        List<Book> list = ListUtil.list(false);
        list.add(Book.builder().id(1).name("java").author("Jack Ma").build());
        list.add(Book.builder().id(2).name("C#").author("Tony Ma").build());
        list.add(Book.builder().id(3).name("java script").author("Jack Chen").build());
        list.add(Book.builder().id(4).name("Python").author("Lin Wong").build());
        list.add(Book.builder().id(5).name("PHP").author("Robin Li").build());
        ModelAndView mv = new ModelAndView();
        mv.addObject("books", list);
        mv.setViewName("demo/index3");
        return mv;
    }
}
