package com.salesianostriana.dam.calesapp.dto;


import com.salesianostriana.dam.calesapp.model.Parada;

import java.util.List;

public record ParadaListDTO(
        Long count,
        List<ParadaDTO> items
) {


    public static ParadaListDTO of(List<Parada> items) {
        return new ParadaListDTO((long) items
                .size(), items
                .stream()
                .map(ParadaDTO::of)
                .toList());
    }
}
