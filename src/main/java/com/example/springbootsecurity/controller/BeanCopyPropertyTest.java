package com.example.springbootsecurity.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.springbootsecurity.demo.entity.Doc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/6/1 10:04
 * @description 属性赋值测试
 */
@Slf4j
public class BeanCopyPropertyTest {
    public static void main(String[] args) {

    }

    @Test
    public void huToolCopy() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 123);
        map.put("b", "属性B");

        Doc doc = new Doc();
        doc.setId(100);
        doc.setName("第一个文档");
        doc.setParams(map);

        //使用 hutool 工具类的 copyProperties 方法，默认情况下是无法复制对象中的 transient 属性的。
        Doc docCopy = BeanUtil.copyProperties(doc, Doc.class);
        System.out.println("copyProperties 复制后的对象属性：" + docCopy.getParams().get("b"));

        //使用 hutool 工具类的 copyProperties 方法，自定义  CopyOptions ，设置 transientSupport 为 false ，
        // 可以复制对象中的 transient 属性。
        Doc docCopy2 = new Doc();
        BeanUtil.copyProperties(doc, docCopy2, CopyOptions.create().setTransientSupport(false));
        System.out.println("copyProperties with copyProperties 复制后的对象属性：" + docCopy2.getParams().get("b"));
    }

    @Test
    public void springCopy() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 123);
        map.put("b", "属性B");

        Doc doc = new Doc();
        doc.setId(100);
        doc.setName("第一个文档");
        doc.setParams(map);

        //使用 spring 官方工具类的 copyProperties 方法，是可以复制对象中的 transient 属性的。
        Doc docCopy = new Doc();
        BeanUtils.copyProperties(doc, docCopy);
        System.out.println("spring BeanUtils copyProperties 复制后的对象属性：" + docCopy.getParams().get("b"));

        Doc docCopy2 = new Doc();

        try {
            //使用 apache 的 copyProperties 方法，是可以复制对象中的 transient 属性的。
            org.apache.commons.beanutils.BeanUtils.copyProperties(docCopy2, doc);

            System.out.println("apache BeanUtils copyProperties 复制后的对象属性：" + docCopy2.getParams().get("b"));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

}
