package com.salesianostriana.dam.calesapp.dto.parada;

import com.salesianostriana.dam.calesapp.model.Parada;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de una parada")
public record ParadaDTO(

        @Schema(description = "ID de la parada", example = "1") Long id,

        @Schema(description = "Nombre de la parada", example = "Plaza de Toros") String nombre,

        @Schema(description = "Latitud de la parada", example = "37.3861") Double lat,

        @Schema(description = "Longitud de la parada", example = "-5.9926") Double lng,

        @Schema(description = "Descripción de la parada", example = "Parada en el acceso a la Plaza de toros de la Maestranza") String descripcion

) {
    public static ParadaDTO fromEntity(Parada parada) {
        return new ParadaDTO(
                parada.getId(),
                parada.getNombre(),
                parada.getLat(),
                parada.getLng(),
                parada.getDescripcion());
    }

    public static ParadaDTO of(Parada parada) {
        return fromEntity(parada);
    }
}
