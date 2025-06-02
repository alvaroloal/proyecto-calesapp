package com.salesianostriana.dam.calesapp.security.jwt.refresh;


import com.salesianostriana.dam.calesapp.security.exceptionhandling.JwtException;

public class RefreshTokenException extends JwtException {
  public RefreshTokenException(String s) {
    super(s);
  }
}
