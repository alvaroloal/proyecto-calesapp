package com.salesianostriana.dam.calesapp.files.service.imgur.error;

public class ImgurBadRequestException extends RuntimeException {

    public ImgurBadRequestException(String message) {
        super(message);
    }
}
