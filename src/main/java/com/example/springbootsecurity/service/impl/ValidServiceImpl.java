package com.example.springbootsecurity.service.impl;

import com.example.springbootsecurity.domain.VO.StudentRequestVO;
import com.example.springbootsecurity.service.ValidService;
import org.springframework.stereotype.Service;

/**
 * @projectName: SpringBootSecurity
 * @package: com.example.springbootsecurity.service.impl
 * @className: ValidServiceImpl
 * @author: HuangLang
 * @description: 参数校验
 * @date: 2021-06-11 下午 3:58
 */
@Service
public class ValidServiceImpl implements ValidService {
    @Override
    public String validIdSize(Integer id) {
        return "id = " + id;
    }

    @Override
    public String validDomain(StudentRequestVO requestVO) {
        return requestVO.toString();
    }
}
