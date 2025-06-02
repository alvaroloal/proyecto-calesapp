package com.salesianostriana.dam.calesapp.files.service.imgur.dto;

public record GetImageResponse(
        String success,
        int status,
        GetImageInfo data
) {
}
