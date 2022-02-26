package com.example.springbootsecurity.domain.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: huang lang
 * @description:
 * @date: 2022/2/26 11:57
 */
public class DocOperateRequest {
    /**
     * 文档id
     */
    @Max(Long.MAX_VALUE)
    @NotNull
    private Long id;

    /**
     * 操作方式
     */
    @NotBlank
    private String type;
}
