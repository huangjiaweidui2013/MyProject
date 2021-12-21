package com.example.springbootsecurity.demo;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.font.OpenType;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontDemo2 {
    public static void main(String[] args) throws IOException, FontFormatException {
        InputStream inputStream = FontDemo.class.getResourceAsStream("/毛笔书法字体(启功体)繁启体.TTF");
        assert inputStream != null;
        Font f = Font.createFont(Font.TRUETYPE_FONT,
                inputStream);
        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics2D = image.createGraphics();
        FontRenderContext context = graphics2D.getFontRenderContext();
        GlyphVector vector = f.createGlyphVector(context, "汉字");
        Point2D point2D = vector.getGlyphPosition(0);
        System.out.println(point2D);
        GlyphMetrics metrics = vector.getGlyphMetrics(0);
        System.out.println(metrics);
        Shape outline = vector.getGlyphOutline(0);
    }
}
