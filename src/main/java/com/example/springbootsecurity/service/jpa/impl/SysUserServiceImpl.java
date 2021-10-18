package com.example.springbootsecurity.service.jpa.impl;

import com.example.springbootsecurity.dao.jpa.SysUserRepository;
import com.example.springbootsecurity.domain.jpa.QSysUserPO;
import com.example.springbootsecurity.domain.jpa.SysUserPO;
import com.example.springbootsecurity.service.jpa.SysUserService;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.service.jpa.impl
 * @className: SysUserServiceImpl
 * @author: HuangLang
 * @description: SysUserService实现类
 * @date: 2021-09-22 上午 11:36
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private JPAQueryFactory jpaQueryFactory;
    @PersistenceContext
    private EntityManager entityManager;

    private final SysUserRepository sysUserRepository;

    public SysUserServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @PostConstruct
    public void init() {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public SysUserPO selectById(Long id) {
        return sysUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<SysUserPO> list(SysUserPO sysUserPO) {
        String username = sysUserPO.getUsername();
        QSysUserPO po = QSysUserPO.sysUserPO;
        JPAQuery<SysUserPO> query = jpaQueryFactory.selectFrom(po);
        if (!StringUtils.isEmpty(username)) {
            query.where(po.username.like("%" + username + "%"));
        }
        return query.fetch();
    }

    @Override
    public SysUserPO save(SysUserPO sysUserPO) {
        return sysUserRepository.save(sysUserPO);
    }
}
