package com.salesianostriana.dam.calesapp.dto.parada;

import com.salesianostriana.dam.calesapp.model.Parada;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una parada")
public record ParadaDTO(
        @Schema(description = "ID de la parada", example = "1") Long id,

        @Schema(description = "Nombre de la parada", example = "Plaza de Toros") String nombre,

        @Schema(description = "Ubicacion de la parada", example = "40.4168,-3.7038") String ubicacion,

        @Schema(description = "Descripción de la parada", example = "Parada en el acceso a la Plaza de toros de la Maestranza") String descripcion) {

    public static ParadaDTO fromEntity(Parada parada) {
        return new ParadaDTO(
                parada.getId(),
                parada.getNombre(),
                parada.getUbicacion(),
                parada.getDescripcion()

        );
    }

    public static ParadaDTO of(Parada parada) {
        return new ParadaDTO(parada.getId(), parada.getNombre(), parada.getUbicacion(), parada.getDescripcion());
    }
}
