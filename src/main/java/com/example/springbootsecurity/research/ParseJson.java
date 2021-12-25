package com.example.springbootsecurity.research;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONReader;
import com.example.springbootsecurity.demo.FontDemo;
import com.example.springbootsecurity.research.model.DwgBlock;
import com.example.springbootsecurity.research.model.DwgBlockEntity;
import com.example.springbootsecurity.research.model.DwgFile;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huang lang
 * @description: 解析复杂json文件
 * @date: 2021/12/22 19:11
 */
public class ParseJson {
    public static void main(String[] args) {
//        parseJsonByOrder();
        parseJsonPart();
    }

    /**
     * 按照顺序从上往下解析
     *
     * @return: void
     * @author: huang lang
     * @date: 2021/12/23 13:50
     * @description:
     */
    public static void parseJsonByOrder() {
        InputStream inputStream = FontDemo.class.getResourceAsStream("/file/big_json_file.json");
        String id = IdUtil.objectId();
        DwgFile dwgFile = DwgFile.builder()
                .id(id)
                .fileName("big_json_file.json")
                .filePath("/json")
                .fileSize(50L)
                .fileType("DWG")
                .fileVersion("1.1.0")
                .userid(100L)
                .build();
        List<DwgBlock> blockList = new ArrayList<>();
        List<DwgBlockEntity> entityList = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
        jsonReader.startObject();
        while (jsonReader.hasNext()) {
            String key = jsonReader.readString();
            System.out.println(key);
            if ("type".equalsIgnoreCase(key)) {
                System.out.println(jsonReader.readString());
            } else if ("extMin".equalsIgnoreCase(key)) {
                System.out.println(jsonReader.readString());
            } else if ("extMax".equalsIgnoreCase(key)) {
                System.out.println(jsonReader.readString());
            } else if ("ZcDbBlockTable".equalsIgnoreCase(key)) {
                jsonReader.startArray();
                long i = 0;
                while (jsonReader.hasNext()) {
                    String blockId = IdUtil.objectId();
                    String block = jsonReader.readString();
                    System.out.println(block);
                    DwgBlockEntity entity = DwgBlockEntity.builder()
                            .entityType("ZcDbBlockTable")
                            .content(block)
                            .pid(blockId)
                            .build();
                    entityList.add(entity);
                    DwgBlock dwgBlock = DwgBlock.builder()
                            .blockType("ZcDbBlockTable")
                            .blockIndex(++i)
                            .id(block)
                            .pid(id)
                            .build();
                    blockList.add(dwgBlock);
                }
                jsonReader.endArray();
            } else if ("ZcDbViewPortTable".equalsIgnoreCase(key)) {
                jsonReader.startArray();
                long i = 0;
                while (jsonReader.hasNext()) {
                    String blockId = IdUtil.objectId();
                    String block = jsonReader.readString();
                    DwgBlockEntity entity = DwgBlockEntity.builder()
                            .entityType("ZcDbViewPortTable")
                            .content(block)
                            .pid(blockId)
                            .build();
                    entityList.add(entity);
                    DwgBlock dwgBlock = DwgBlock.builder()
                            .blockType("ZcDbViewPortTable")
                            .blockIndex(++i)
                            .id(block)
                            .pid(id)
                            .build();
                    blockList.add(dwgBlock);
                }
                jsonReader.endArray();
            } else {
                //不需要的数据，也必须读，可以不做处理
                System.out.println(jsonReader.readString());
            }
        }
        jsonReader.endObject();
        System.out.println("block size: " + blockList.size());
        System.out.println("entity size: " + entityList.size());
    }


    public static void parseJsonPart() {
        InputStream inputStream = FontDemo.class.getResourceAsStream("/file/big_json_file.json");
        String id = IdUtil.objectId();
        DwgFile dwgFile = DwgFile.builder()
                .id(id)
                .fileName("big_json_file.json")
                .filePath("/json")
                .fileSize(50L)
                .fileType("DWG")
                .fileVersion("1.1.0")
                .userid(100L)
                .build();
        List<DwgBlock> blockList = new ArrayList<>();
        List<DwgBlockEntity> entityList = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(new InputStreamReader(inputStream));
        jsonReader.startObject();
        StringBuilder fileExtra = new StringBuilder();
        while (jsonReader.hasNext()) {
            String key = jsonReader.readString();
            System.out.println(key);
            if ("ZcDbBlockTable".equalsIgnoreCase(key)) {
                jsonReader.startArray();
                long i = 0;
                while (jsonReader.hasNext()) {
                    jsonReader.startObject();
                    String blockId = IdUtil.objectId();
                    DwgBlock dwgBlock = DwgBlock.builder()
                            .blockType("ZcDbBlockTable")
                            .blockIndex(++i)
                            .id(blockId)
                            .pid(id)
                            .build();
                    StringBuilder blockExtra = new StringBuilder();
                    while (jsonReader.hasNext()) {
                        String blockKey = jsonReader.readString();
                        if ("entity".equalsIgnoreCase(blockKey)) {
                            jsonReader.startArray();
                            while (jsonReader.hasNext()) {
                                DwgBlockEntity entity = DwgBlockEntity.builder()
                                        .entityType("ZcDbBlockTable")
                                        .content(jsonReader.readString())
                                        .pid(blockId)
                                        .build();
                                entityList.add(entity);
                            }
                            jsonReader.endArray();
                        } else {
                            //不需要的数据，也必须读，可以不做处理
                            String blockStr = wrapReadString(blockKey) + ":" + wrapReadString(jsonReader.readString());
                            blockExtra.append(StringUtils.isEmpty(blockExtra.toString()) ? blockStr : "," + blockStr);
                        }
                    }
                    dwgBlock.setExtra(blockExtra.toString());
                    blockList.add(dwgBlock);
                    jsonReader.endObject();
                }
                jsonReader.endArray();
            } else if ("ZcDbViewPortTable".equalsIgnoreCase(key)) {
                jsonReader.startArray();
                long i = 0;
                while (jsonReader.hasNext()) {
                    String blockId = IdUtil.objectId();
                    String block = jsonReader.readString();
                    DwgBlockEntity entity = DwgBlockEntity.builder()
                            .entityType("ZcDbViewPortTable")
                            .content(block)
                            .pid(blockId)
                            .build();
                    entityList.add(entity);
                    DwgBlock dwgBlock = DwgBlock.builder()
                            .blockType("ZcDbViewPortTable")
                            .blockIndex(++i)
                            .id(block)
                            .pid(id)
                            .build();
                    blockList.add(dwgBlock);
                }
                jsonReader.endArray();
            } else {
                //不需要的数据，也必须读，可以不做处理
                String readString = wrapReadString(key) + ":" + wrapReadString(jsonReader.readString());
                System.out.println(readString);
                fileExtra.append(StringUtils.isEmpty(fileExtra.toString()) ? readString : "," + readString);
            }
        }
        jsonReader.endObject();
        System.out.println(fileExtra);
        dwgFile.setExtra(fileExtra.toString());
        System.out.println("block size: " + blockList.size());
        System.out.println("entity size: " + entityList.size());
    }

    private static String wrapReadString(String s) {
        if (StringUtils.isNotEmpty(s)) {
            if (!StringUtils.isNumeric(s) && !s.startsWith("[") && !s.startsWith("{")) {
                return "\"" + s + "\"";
            }
        }
        return s;
    }
}
