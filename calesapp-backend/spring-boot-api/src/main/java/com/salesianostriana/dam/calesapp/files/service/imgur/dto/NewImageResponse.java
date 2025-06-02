package com.salesianostriana.dam.calesapp.files.service.imgur.dto;

public record NewImageResponse(
        String success,
        int status,
        NewImageInfo data
) {
}
