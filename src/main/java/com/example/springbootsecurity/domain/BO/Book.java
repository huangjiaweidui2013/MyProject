package com.example.springbootsecurity.domain.BO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.domain.BO
 * @className: Book
 * @author: HuangLang
 * @description:
 * @date: 2021-06-24 上午 9:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    private String name;
    private String author;
}
