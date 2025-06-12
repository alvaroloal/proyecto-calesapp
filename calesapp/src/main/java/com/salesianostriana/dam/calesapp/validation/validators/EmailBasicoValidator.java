package com.salesianostriana.dam.calesapp.validation.validators;

import com.salesianostriana.dam.calesapp.validation.annotations.EmailBasico;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailBasicoValidator implements ConstraintValidator<EmailBasico, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty())
            return true;

        return value.contains("@");
    }
}
