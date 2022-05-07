package com.example.springbootsecurity.test;

import cn.hutool.core.util.IdUtil;
import com.example.springbootsecurity.research.model.DwgBlock;
import org.springframework.beans.BeanUtils;

import java.io.File;

/**
 * @author: huang lang
 * @description: 杂项测试代码编写
 * @date: 2022/1/4 16:40
 */
public class OtherTest {
    public static void main(String[] args) {
        long id = IdUtil.getSnowflake(1, 1).nextId();
        System.out.println(id);

        String fastSimpleUUID = IdUtil.fastSimpleUUID();
        System.out.println(fastSimpleUUID);
        String fastUUID = IdUtil.fastUUID();
        System.out.println(fastUUID);

        String str = "shop//test.dwg";
        String substring = str.substring(str.lastIndexOf("."));
        System.out.println(substring);

        File file = new File("D:\\test123\\123//234\\text.txt");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            System.out.println(mkdirs);
        }

        DwgBlock dwgBlock = DwgBlock.builder().pid("1233").build();
        BeanUtils.copyProperties(dwgBlock, DwgBlock.builder().build());

    }
}
