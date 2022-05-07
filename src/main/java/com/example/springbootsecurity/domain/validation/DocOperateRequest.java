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
    //使用 groups 来指定校验顺序
    @NotNull(groups = VerifySequence.N0.class)
    private Long id;

    /**
     * 操作方式
     */
    //使用 groups 来指定校验顺序
    @NotBlank(groups = VerifySequence.N1.class)
    private String type;
}
