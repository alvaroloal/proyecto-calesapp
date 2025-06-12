package com.salesianostriana.dam.calesapp.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.salesianostriana.dam.calesapp.validation.validators.FechaFuturaValidator;

@Documented
@Constraint(validatedBy = FechaFuturaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaFutura {

    String message() default "La fecha debe ser futura";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
