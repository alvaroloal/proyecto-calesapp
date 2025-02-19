package com.salesianostriana.dam.calesapp.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.dam.calesapp.user.model.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String refreshToken,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String verificationToken

) {

    public static UserResponse of (User user) {
        return new UserResponse(user.getId(), user.getUsername(), null, null, null);
    }

    public static UserResponse of (User user, String token, String refreshToken) {
        return new UserResponse(user.getId(), user.getUsername(), token, refreshToken, null);
    }

    public static UserResponse of(User user, String verificationToken) {

        return new UserResponse(user.getId(), user.getUsername(), null, null, verificationToken);
    }

}
