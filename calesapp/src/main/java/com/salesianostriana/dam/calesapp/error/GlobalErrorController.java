package com.salesianostriana.dam.calesapp.error;

import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
@Order(2)
public class GlobalErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        detalle.setTitle("Entidad no encontrada");
        detalle.setType(URI.create("https://www.salesianos-triana.edu/errors/entity-not-found"));
        return detalle;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationError(AuthenticationException ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, ex.getMessage());
        detalle.setTitle("Operación no permitida");
        detalle.setType(URI.create("https://www.salesianos-triana.edu/errors/authentication-failed"));
        return detalle;
    }
}
