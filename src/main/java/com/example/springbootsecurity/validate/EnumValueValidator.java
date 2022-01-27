package com.example.springbootsecurity.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author: huang lang
 * @description: EnumValue 注解校验类
 * @date: 2022/1/27 17:01
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private String[] strValues;
    private int[] intValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        strValues = constraintAnnotation.strValues();
        intValues = constraintAnnotation.intValues();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean b = false;
        if (value instanceof String) {
            b = Arrays.asList(strValues).contains(value);
        } else if (value instanceof Integer) {
            b = Arrays.stream(intValues).anyMatch(i -> i == (Integer) value);
        }
        return b;
    }

}
