package com.salesianostriana.dam.calesapp.user.dto;

public record CreateUsuarioRequest(
        String username, String password, String verifyPassword
) {
}
