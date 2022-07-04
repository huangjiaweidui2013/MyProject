package com.example.springbootsecurity.validate;

import org.apache.commons.lang3.ArrayUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

/**
 * @author: huang lang
 * @description: EnumValue 注解校验类
 * @date: 2022/1/27 17:01
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private boolean isRequired;
    private String[] strValues;
    private int[] intValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        isRequired = constraintAnnotation.isRequired();
        strValues = constraintAnnotation.strValues();
        intValues = constraintAnnotation.intValues();
        Class<?>[] anEnum = constraintAnnotation.belongEnum();
        Collection<Object> values = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(anEnum)) {
            Arrays.stream(anEnum).forEach(
                    enumValue -> {
                        Object[] enumConstants = enumValue.getEnumConstants();
                        Enum[] enums = Arrays.copyOf(enumConstants, enumConstants.length, Enum[].class);
                        Collections.addAll(values, enums);
                    }
            );
        }
        String[] str = values.stream().map(Object::toString).toArray(String[]::new);
        strValues = ArrayUtils.addAll(strValues, str);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean b = false;
        if (!isRequired && (Objects.isNull(value))) {
            return true;
        }
        if (value instanceof String) {
            b = Arrays.asList(strValues).contains(value);
        } else if (value instanceof Integer) {
            b = Arrays.stream(intValues).anyMatch(i -> i == (Integer) value);
        }
        return b;
    }

}
