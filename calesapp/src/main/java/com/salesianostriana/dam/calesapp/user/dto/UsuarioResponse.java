package com.salesianostriana.dam.calesapp.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.dam.calesapp.user.model.Usuario;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String username,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String refreshToken,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String verificationToken

) {

    public static UsuarioResponse of (Usuario user) {
        return new UsuarioResponse(user.getId(), user.getUsername(), null, null, null);
    }

    public static UsuarioResponse of (Usuario user, String token, String refreshToken) {
        return new UsuarioResponse(user.getId(), user.getUsername(), token, refreshToken, null);
    }

    public static UsuarioResponse of(Usuario user, String verificationToken) {

        return new UsuarioResponse(user.getId(), user.getUsername(), null, null, verificationToken);
    }

}
