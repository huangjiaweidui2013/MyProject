package com.example.springbootsecurity.research;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * @author: huang lang
 * @description: 生成json文件
 * @date: 2021/12/22 17:49
 */
public class GenerateJson {

    private static final int BLOCK_TABLE_SIZE = 500;

    private static final int VIEW_PORT_TABLE_SIZE = 200;

    private static final int ENTITY_SIZE_MIN = 200;

    private static final int ENTITY_SIZE_MAX = 500;

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        File file = new File("super_big_json_file.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("type", "ZJX2021");
        ArrayNode arrayNode1 = mapper.createArrayNode();
        arrayNode1.add(0);
        arrayNode1.add(1);
        arrayNode1.add(2);
        root.set("extMin", arrayNode1);
        root.set("extMax", arrayNode1);

        ArrayNode blockArray = mapper.createArrayNode();
        for (int i = 0; i < BLOCK_TABLE_SIZE; i++) {
            int randomInt = RandomUtil.randomInt(ENTITY_SIZE_MIN, ENTITY_SIZE_MAX);
            ObjectNode tmpObj = mapper.createObjectNode();
            ArrayNode tmpArr = mapper.createArrayNode();
            for (int j = 0; j < randomInt; j++) {
                tmpArr.add(generateEntity(mapper));
            }
            tmpObj.set("entity", tmpArr);
            tmpObj.set("name", new TextNode("_DIMX"));
            tmpObj.set("type", new TextNode("BlockTableRecord"));
            blockArray.add(tmpObj);
        }
        root.set("ZcDbBlockTable", blockArray);

        ArrayNode viewArray = mapper.createArrayNode();
        for (int i = 0; i < VIEW_PORT_TABLE_SIZE; i++) {
            viewArray.add(generateViewPort(mapper));
        }
        root.set("ZcDbViewPortTable", viewArray);

        mapper.writeValue(new FileOutputStream(file), root);
        Instant end = Instant.now();
        System.out.println("生成文件耗时(ms): " + Duration.between(start, end).toMillis());
    }

    public static ObjectNode generateEntity(ObjectMapper mapper) {
        ObjectNode entityNode = mapper.createObjectNode();
        entityNode.set("color", new DoubleNode(Math.random() * 10000));
        ArrayNode tmpArrayNode1 = mapper.createArrayNode();
        tmpArrayNode1.add(1);
        tmpArrayNode1.add(2);
        tmpArrayNode1.add(3);
        ArrayNode tmpArrayNode2 = mapper.createArrayNode();
        tmpArrayNode2.add(new DoubleNode(Math.random() * 10000));
        tmpArrayNode2.add(new DoubleNode(Math.random() * 10000));
        tmpArrayNode2.add(0);
        entityNode.set("endPoint", tmpArrayNode1);
        entityNode.set("handld_high", new DoubleNode(Math.random() * 10000));
        entityNode.set("handld_low", new DoubleNode(Math.random() * 10000));
        entityNode.set("normal", tmpArrayNode1);
        entityNode.set("startPoint", tmpArrayNode2);
        entityNode.set("type", new TextNode("ZcDbLine"));
        return entityNode;
    }

    public static ObjectNode generateViewPort(ObjectMapper mapper) {
        ObjectNode viewPort = mapper.createObjectNode();
        ObjectNode obj = mapper.createObjectNode();
        obj.set("x", new DoubleNode(Math.random() * 10000));
        obj.set("y", new DoubleNode(Math.random() * 10000));
        viewPort.set("centerPoint", obj);
        viewPort.set("handld_high", new DoubleNode(Math.random() * 10000));
        viewPort.set("handld_low", new DoubleNode(Math.random() * 10000));
        viewPort.set("lensLength", new DoubleNode(Math.random() * 10000));
        viewPort.set("name", new TextNode("Active"));
        ArrayNode tmpArrayNode1 = mapper.createArrayNode();
        tmpArrayNode1.add(1);
        tmpArrayNode1.add(2);
        tmpArrayNode1.add(3);
        ArrayNode tmpArrayNode2 = mapper.createArrayNode();
        tmpArrayNode2.add(new DoubleNode(Math.random() * 10000));
        tmpArrayNode2.add(new DoubleNode(Math.random() * 10000));
        tmpArrayNode2.add(0);
        viewPort.set("target", tmpArrayNode1);
        viewPort.set("type", new TextNode("ZcDbViewPortTableRecord"));
        viewPort.set("viewDirection", tmpArrayNode1);
        viewPort.set("viewHeight", new DoubleNode(Math.random() * 10000));
        viewPort.set("viewWidth", new DoubleNode(Math.random() * 10000));
        return viewPort;
    }
}
