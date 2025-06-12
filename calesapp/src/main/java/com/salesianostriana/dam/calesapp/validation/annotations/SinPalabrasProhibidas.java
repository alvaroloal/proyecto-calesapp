package com.salesianostriana.dam.calesapp.validation.annotations;

import com.salesianostriana.dam.calesapp.validation.validators.SinPalabrasProhibidasValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SinPalabrasProhibidasValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface SinPalabrasProhibidas {

    String message()

    default "El texto contiene palabras no permitidas";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] palabras() default {
            "tonto", "estúpido", "idiota", "imbécil", "gilipollas", "cabrón", "mierda",
    };
}
