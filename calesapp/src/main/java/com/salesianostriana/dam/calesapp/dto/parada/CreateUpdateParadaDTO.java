package com.salesianostriana.dam.calesapp.dto.parada;

import com.salesianostriana.dam.calesapp.model.Parada;

public record CreateUpdateParadaDTO(

        String nombre,
        String ubicacion,
        String descripcion
) {

    public static CreateUpdateParadaDTO fromEntity(Parada parada) {
        return new CreateUpdateParadaDTO(
                parada.getNombre(),
                parada.getUbicacion(),
                parada.getDescripcion()
        );
    }
}
