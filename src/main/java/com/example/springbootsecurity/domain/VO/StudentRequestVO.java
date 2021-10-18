package com.example.springbootsecurity.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.domain.VO
 * @className: StudentRequestVO
 * @author: HuangLang
 * @description: 参数校验
 * @date: 2021-06-11 下午 2:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestVO {
    @NotNull //被注释的元素必须不为 null
    private Integer sId;

    @NotEmpty(message = "sName必填") //被注释的字符串的不能为 null 也不能为空
    private String sName;

    @Min(1) //被注释的元素必须是一个数字，其值必须大于等于指定的最小值
    @Max(20) //被注释的元素必须是一个数字，其值必须小于等于指定的最大值
    private Integer sAge;

    @Email //被注释的元素必须是 Email 格式
    private String email;

    @NotBlank // 被注释的字符串非 null，并且必须包含一个非空白字符
    private String sex;

    @Past //被注释的元素必须是一个过去的日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @AssertTrue //被注释的元素必须为 true
    private boolean isStudent;

}
