package com.salesianostriana.dam.calesapp.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.salesianostriana.dam.calesapp.validation.validators.FechaPasadaValidator;

@Documented
@Constraint(validatedBy = FechaPasadaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaPasada {

    String message() default "La fecha no puede ser futura";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
