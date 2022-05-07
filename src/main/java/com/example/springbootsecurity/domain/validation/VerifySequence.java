package com.example.springbootsecurity.domain.validation;

import javax.validation.GroupSequence;

/**
 * @author huang lang
 * @description: 用于定义字段校验顺序
 * @date 2022/3/22 14:20
 */
@GroupSequence({
        VerifySequence.N0.class, VerifySequence.N1.class, VerifySequence.N2.class, VerifySequence.N3.class,
        VerifySequence.N4.class, VerifySequence.N5.class, VerifySequence.N6.class, VerifySequence.N7.class,
        VerifySequence.N8.class, VerifySequence.N9.class
})
public interface VerifySequence {
    interface N0 {
    }

    interface N1 {
    }

    interface N2 {
    }

    interface N3 {
    }

    interface N4 {
    }

    interface N5 {
    }

    interface N6 {
    }

    interface N7 {
    }

    interface N8 {
    }

    interface N9 {
    }
}
