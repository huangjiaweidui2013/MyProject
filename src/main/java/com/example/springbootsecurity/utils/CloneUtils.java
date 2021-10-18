package com.example.springbootsecurity.utils;

import lombok.Builder;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.utils
 * @className: CloneUtils
 * @author: HuangLang
 * @description: 克隆工具类
 * @date: 2021-07-06 上午 9:58
 */
public class CloneUtils {
    public static void main(String[] args) throws CloneNotSupportedException {
        Apple apple = Apple.builder().color("red").total(100).build();
        Fruit fruit = Fruit.builder().apple(apple).name("水果").price(5.20).build();
        Fruit fruit1 = deepClone(fruit);
        System.out.println(fruit1);
    }

    public static <T extends Serializable> T deepClone(T t) throws CloneNotSupportedException {
        try {
            // 保存对象为字节数组
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(t);
            // 从字节数组中读取克隆对象
            InputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bin);
            return (T) (in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException cloneNotSupportedException = new CloneNotSupportedException();
            e.initCause(cloneNotSupportedException);
            throw cloneNotSupportedException;
        }
    }
}

@Data
@Builder
class Fruit implements Serializable {
    Apple apple;
    String name;
    Double price;
}

@Data
@Builder
class Apple implements Serializable {
    String color;
    Integer total;
}
