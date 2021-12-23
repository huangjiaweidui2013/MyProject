package com.example.springbootsecurity.research.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * dwg文件block块中的entity内容对象 dwg_block_entity
 *
 * @author huang lang
 * @date 2021-12-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DwgBlockEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 父级id
     */
    private String pid;

    /**
     * entity类型
     */
    private String entityType;

    /**
     * json字符串内容
     */
    private String content;

}
