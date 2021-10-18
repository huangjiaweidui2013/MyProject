package com.example.springbootsecurity.dao.jpa;

import com.example.springbootsecurity.domain.jpa.SysUserPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.dao.jpa
 * @className: SysUserRepository
 * @author: HuangLang
 * @description: 一个Dao接口来操作实体类对应的数据表（Repository）
 * @date: 2021-09-22 上午 11:14
 */
public interface SysUserRepository extends JpaRepository<SysUserPO, Long> {
}
