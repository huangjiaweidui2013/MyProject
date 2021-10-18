package com.example.springbootsecurity.service.jpa;

import com.example.springbootsecurity.domain.jpa.SysUserPO;

import java.util.List;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.service.jpa
 * @className: SysUserService
 * @author: HuangLang
 * @description: SysUser接口
 * @date: 2021-09-22 上午 11:25
 */
public interface SysUserService {
    SysUserPO selectById(Long id);

    List<SysUserPO> list(SysUserPO sysUserPO);

    SysUserPO save(SysUserPO sysUserPO);
}
