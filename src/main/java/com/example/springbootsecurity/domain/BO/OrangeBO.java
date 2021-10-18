package com.example.springbootsecurity.domain.BO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.domain.BO
 * @className: OrangeBO
 * @author: HuangLang
 * @description: 橘子
 * @date: 2021-07-23 下午 5:51
 */
@Data
@Builder
public class OrangeBO {
    private Integer id;
    private String color;
    private Double price;
    private Date producedDate;
}
