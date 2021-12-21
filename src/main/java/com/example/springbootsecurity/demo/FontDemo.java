package com.example.springbootsecurity.demo;

import java.awt.*;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.io.*;
import java.text.AttributedCharacterIterator;

public class FontDemo {
    public static void main(String[] args) throws IOException, FontFormatException {
        InputStream inputStream = FontDemo.class.getResourceAsStream("/毛笔书法字体(启功体)繁启体.TTF");
        assert inputStream != null;
        Font f = Font.createFont(Font.TRUETYPE_FONT,
                inputStream);
        int i = f.canDisplayUpTo("123జABC");
        AttributedCharacterIterator.Attribute[] attributes = f.getAvailableAttributes();
        for (AttributedCharacterIterator.Attribute attribute : attributes) {
            System.out.println(attribute);
        }
        System.out.println(i);
        String name = f.getName();
        //关于名称
        System.out.println("name: " + name + ", fontName: " + f.getFontName() + ", family: " + f.getFamily());
        System.out.println("样式：" + f.getStyle() + ", 粗体: " + f.isBold() + ", 常规: " + f.isPlain() + ", 斜体: " + f.isItalic());
        System.out.println("点大小（基于大约1/72英寸）：" + f.getSize() + ", 是否能显示字符: " + f.canDisplayUpTo("abcgdj46656阿汉华夏#￥%￥"));

    }
}
