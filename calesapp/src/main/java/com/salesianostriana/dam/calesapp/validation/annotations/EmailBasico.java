package com.salesianostriana.dam.calesapp.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.salesianostriana.dam.calesapp.validation.validators.EmailBasicoValidator;

@Documented
@Constraint(validatedBy = EmailBasicoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailBasico {

    String message() default "El correo debe contener '@'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
