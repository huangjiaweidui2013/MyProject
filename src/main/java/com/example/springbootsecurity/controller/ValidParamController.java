package com.example.springbootsecurity.controller;

import com.example.springbootsecurity.domain.VO.StudentRequestVO;
import com.example.springbootsecurity.domain.validation.SysDocCopyOrMoveRequest;
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
/*@Validated：可以用在类型、方法和方法参数上。但是不能用在成员属性（字段）上
@Valid：可以用在方法、构造函数、方法参数和成员属性（字段）上
        两者是否能用于成员属性（字段）上直接影响能否提供嵌套验证的功能。
@Validated：用在方法入参上无法单独提供嵌套验证功能。不能用在成员属性（字段）上，也无法提示框架进行嵌套验证。能配合嵌套验证注解@Valid进行嵌套验证。

@Valid：用在方法入参上无法单独提供嵌套验证功能。能够用在成员属性（字段）上，提示验证框架进行嵌套验证。能配合嵌套验证注解@Valid进行嵌套验证。
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

    /**
     * 嵌套属性校验
     *
     * @param moveRequest
     */
    @PostMapping("/move")
    public void getSysDocOperate(@RequestBody @Validated SysDocCopyOrMoveRequest moveRequest) {

    }
}
