package com.salesianostriana.dam.calesapp.validation.validators;

import com.salesianostriana.dam.calesapp.validation.annotations.SinPalabrasProhibidas;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SinPalabrasProhibidasValidator implements ConstraintValidator<SinPalabrasProhibidas, String> {

    private String[] palabrasProhibidas;

    @Override
    public void initialize(SinPalabrasProhibidas constraintAnnotation) {
        this.palabrasProhibidas = constraintAnnotation.palabras();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        String contenido = value.toLowerCase();

        for (String palabra : palabrasProhibidas) {
            if (contenido.contains(palabra.toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}
