package com.salesianostriana.dam.calesapp.user.dto;

public record CreateUserRequest(
        String username, String password, String verifyPassword
) {
}
