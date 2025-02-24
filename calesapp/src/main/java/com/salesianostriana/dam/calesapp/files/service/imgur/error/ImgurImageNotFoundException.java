package com.salesianostriana.dam.calesapp.files.service.imgur.error;

public class ImgurImageNotFoundException extends RuntimeException {
    public ImgurImageNotFoundException(String message) {
        super(message);
    }
}
