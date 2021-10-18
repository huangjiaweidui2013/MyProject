package com.example.springbootsecurity.controller;

import com.example.springbootsecurity.domain.VO.StudentRequestVO;
import com.example.springbootsecurity.service.ValidService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller
 * @className: ValidParamController
 * @author: HuangLang
 * @description: 验证请求参数(Path Variables 和 Request Parameters)
 * @date: 2021-06-11 下午 3:47
 */
// 我们还可以验证任何Spring组件的输入，而不只是验证控制器Controller级别的输入，例如我们可以使用@Validated和@Valid注释的组合来校验service层的输入参数。
// 一定一定不要忘记在类上加上 Validated 注解了，这个参数可以告诉 Spring 去校验方法参数。
@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor
public class ValidParamController {
    private ValidService validService;

    @GetMapping("/valid/{id}")
    public String validParam(@Valid @PathVariable("id") @Min(value = 1, message = "id必须大于等于1") @Max(value = 20,
            message = "id必须小于等于20") Integer id) {
        return "SUCCESS";
    }

    @GetMapping("/valid/service/{id}")
    public String validService(@PathVariable("id") Integer id) {
        return validService.validIdSize(id);
    }

    @PostMapping("/valid/service/domain")
    public String validServiceDomain(@RequestBody StudentRequestVO requestVO) {
        return validService.validDomain(requestVO);
    }
}
