package com.salesianostriana.dam.calesapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ContactoDuplicadoException extends RuntimeException {
    public ContactoDuplicadoException(String message) {
        super(message);
    }
}
