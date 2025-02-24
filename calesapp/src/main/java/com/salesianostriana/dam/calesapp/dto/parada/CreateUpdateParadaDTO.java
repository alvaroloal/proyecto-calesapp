package com.salesianostriana.dam.calesapp.dto.parada;

import com.salesianostriana.dam.calesapp.model.Parada;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Datos de una parada")
public record CreateUpdateParadaDTO(

        @Schema(description = "Nombre de la parada", example = "Plaza de Toros")
        String nombre,

        @Schema(description = "Ubicacion de la parada", example = "40.4168,-3.7038")
        String ubicacion,

        @Schema(description = "Descripción de la parada", example = "Parada en el acceso a la Plaza de toros de la Maestranza")
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
