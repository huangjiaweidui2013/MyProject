package com.example.springbootsecurity.domain.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author: huang lang
 * @description:
 * @date: 2022/2/26 11:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDocCopyOrMoveRequest {

    /**
     * 目标list  嵌套属性校验，必须加上这个注解@Valid
     */
    @Valid
    //使用 groups 来指定校验顺序
    @Size(min = 1, groups = VerifySequence.N1.class)
    private List<DocOperateRequest> list;

    /**
     * 目标id
     */
    @Max(Long.MAX_VALUE)
    //使用 groups 来指定校验顺序
    @NotNull(groups = VerifySequence.N0.class)
    private Long id;

}
