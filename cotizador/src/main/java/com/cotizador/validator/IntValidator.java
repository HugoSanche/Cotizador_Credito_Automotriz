package com.cotizador.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IntValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Integer.class.equals(clazz) || int.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Integer intValue = (Integer) target;
        //  Check if the integer is greater than zero
        if (intValue <= 0) {
            errors.rejectValue("intValue", "intValue.invalid", "Integer value must be greater than zero.");
        }
    }
}
