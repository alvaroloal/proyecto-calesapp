package com.salesianostriana.dam.calesapp.error;

import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalErrorController
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND,
                        ex.getMessage());
        result.setTitle("Entidad no encontrada");
        result.setType(URI.create("https://www.salesianos-triana.edu/errors/entity-not-found"));
        return result;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationError(AuthenticationException ex){
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                        ex.getMessage());
        result.setTitle("Operación no permitida");
        return result;
    }


}
