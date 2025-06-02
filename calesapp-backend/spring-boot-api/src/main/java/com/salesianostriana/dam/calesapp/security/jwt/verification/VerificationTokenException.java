package com.salesianostriana.dam.calesapp.security.jwt.verification;


import com.salesianostriana.dam.calesapp.security.exceptionhandling.JwtException;

public class VerificationTokenException extends JwtException {
    public VerificationTokenException(String message) {
        super(message);
    }
}
