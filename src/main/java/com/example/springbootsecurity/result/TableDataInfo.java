package com.example.springbootsecurity.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.result
 * @className: TableDataInfo
 * @author: HuangLang
 * @description: 表格数据返回
 * @date: 2021-07-26 下午 6:47
 */
@Data
public class TableDataInfo implements Serializable {
    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<?> rows;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 额外的内容
     */
    private Map<String, Object> extra;

    /**
     * 表格数据对象
     */
    public TableDataInfo() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total) {
        this.rows = list;
        this.total = total;
    }

}
