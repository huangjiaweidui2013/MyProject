package com.example.springbootsecurity.demo.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/6/1 9:59
 * @description 包含 map 类型属性的实体对象
 */
public class Doc {
    private Integer id;
    private String name;
    private transient Map<String, Object> params;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
