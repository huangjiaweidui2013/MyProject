package com.example.springbootsecurity.domain.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.domain.entity
 * @className: SysUser
 * @author: HuangLang
 * @description: 系统用户
 * @date: 2021-09-22 上午 10:30
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Table(name = "sys_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SysUserPO implements Serializable {
    //这是一个主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer age;

    @Column(name = "nick_name", length = 50)
    private String nickname;

    //这是和数据表对应的一个列
    @Column(name = "user_name", length = 50)
    private String username;

    //省略默认列名就是属性名
    @Column
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private LocalDateTime createTime;

}

