package com.example.springbootsecurity.research.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 图纸中的block块对象 dwg_block
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
public class DwgBlock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String pid;

    /**
     * 类型：ZcDbBlock、ZcDbViewPort等
     */
    private String blockType;

    /**
     * block序号
     */
    private Long blockIndex;

}
