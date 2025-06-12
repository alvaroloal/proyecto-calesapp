package com.salesianostriana.dam.calesapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import com.salesianostriana.dam.calesapp.validation.annotations.FechaPasada;

public class FechaPasadaValidator implements ConstraintValidator<FechaPasada, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        return !value.isAfter(LocalDate.now());
    }
}
