package com.salesianostriana.dam.calesapp.user.dto;

import com.salesianostriana.dam.calesapp.user.model.UsuarioRole;

public record CreateUsuarioRequest(
        String username, String password, String verifyPassword, UsuarioRole rol
) {
}
