package com.example.springbootsecurity.controller.jpa;

import cn.hutool.core.util.RandomUtil;
import com.example.springbootsecurity.domain.jpa.SysUserPO;
import com.example.springbootsecurity.result.AjaxResult;
import com.example.springbootsecurity.service.jpa.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.controller.jpa
 * @className: SysUserController
 * @author: HuangLang
 * @description: SysUser接口
 * @date: 2021-09-22 下午 2:16
 */
@AllArgsConstructor
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    private SysUserService sysUserService;

    @RequestMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") Long id) {
        return AjaxResult.success(sysUserService.selectById(id));
    }

    @RequestMapping("/save")
    public AjaxResult save() {
        String randomString = RandomUtil.randomString(10);
        SysUserPO po =
                SysUserPO.builder()
                        .age(RandomUtil.randomInt(0, 20))
                        .nickname(randomString)
                        .createTime(LocalDateTime.now())
                        .username(randomString)
                        .password(new BCryptPasswordEncoder().encode(randomString))
                        .build();
        return AjaxResult.success(sysUserService.save(po));
    }

    @GetMapping("/list")
    public AjaxResult list() {
        SysUserPO po =
                SysUserPO.builder()
//                        .age(RandomUtil.randomInt(0, 20))
                        .username("a")
//                        .createTime(LocalDateTime.now())
//                        .username(randomString)
//                        .password(new BCryptPasswordEncoder().encode(randomString))
                        .build();
        return AjaxResult.success(sysUserService.list(po));
    }

}
