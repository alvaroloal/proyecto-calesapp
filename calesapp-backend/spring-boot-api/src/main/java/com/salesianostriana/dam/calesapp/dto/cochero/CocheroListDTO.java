package com.salesianostriana.dam.calesapp.dto.cochero;

import com.salesianostriana.dam.calesapp.model.Cochero;

import java.util.List;

public record CocheroListDTO(
        Long count,
        List<CocheroDTO> items
) {


    public static CocheroListDTO of(List<Cochero> items) {
        return new CocheroListDTO((long) items
                .size(), items
                .stream()
                .map(CocheroDTO::of)
                .toList());
    }
}
