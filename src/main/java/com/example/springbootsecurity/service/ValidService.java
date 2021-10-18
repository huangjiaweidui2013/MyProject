package com.example.springbootsecurity.service;

import com.example.springbootsecurity.domain.VO.StudentRequestVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.service
 * @className: ValidService
 * @author: HuangLang
 * @description: 验证service层的参数 @Validated注解加在接口上 @Valid注解加在接口的需要校验参数的抽象方法上
 * @date: 2021-06-11 下午 3:56
 */
@Validated
public interface ValidService {
    String validIdSize(@Valid @Min(value = 1, message = "id必须大于等于1") @Max(value = 20,
            message = "id必须小于等于20") Integer id);

    String validDomain(@Valid StudentRequestVO requestVO);
}
