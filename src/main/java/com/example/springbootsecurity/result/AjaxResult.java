package com.example.springbootsecurity.result;

import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.util.HashMap;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.result
 * @className: AjaxResult
 * @author: HuangLang
 * @description: 通用操作返回
 * @date: 2021-07-26 下午 6:25
 */
@Data
public class AjaxResult extends HashMap<String, Object> {
    /**
     * 状态码
     */
    private static final String CODE_TAG = "code";
    /**
     * 返回消息
     */
    private static final String MSG_TAG = "msg";
    /**
     * 数据对象
     */
    private static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.HTTP_OK, msg, data);
    }

    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }

    public static AjaxResult error(int code, String msg, Object data) {
        return new AjaxResult(code, msg, data);
    }
}
