package com.salesianostriana.dam.calesapp.dto.parada;

import com.salesianostriana.dam.calesapp.model.Parada;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos de una parada")
public record CreateUpdateParadaDTO(

        @Schema(description = "Nombre de la parada", example = "Plaza de Toros") @NotBlank(message = "El nombre no puede estar vacío") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") String nombre,

        @Schema(description = "Latitud de la parada", example = "37.3861") @NotNull(message = "La latitud no puede ser nula") @DecimalMin(value = "-90.0", message = "Latitud no válida") @DecimalMax(value = "90.0", message = "Latitud no válida") Double lat,

        @Schema(description = "Longitud de la parada", example = "-5.9926") @NotNull(message = "La longitud no puede ser nula") @DecimalMin(value = "-180.0", message = "Longitud no válida") @DecimalMax(value = "180.0", message = "Longitud no válida") Double lng,

        @Schema(description = "Descripción de la parada", example = "Parada junto a la catedral") @NotBlank(message = "La descripción no puede estar vacía") @Size(max = 255, message = "La descripción no puede superar los 255 caracteres") String descripcion

) {
    public static CreateUpdateParadaDTO fromEntity(Parada parada) {
        return new CreateUpdateParadaDTO(
                parada.getNombre(),
                parada.getLat(),
                parada.getLng(),
                parada.getDescripcion());
    }
}
