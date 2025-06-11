package com.salesianostriana.dam.calesapp.dto.parada;

import com.salesianostriana.dam.calesapp.model.Parada;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Datos de una parada")
public record CreateUpdateParadaDTO(

        @Schema(description = "Nombre de la parada", example = "Plaza de Toros") @NotBlank(message = "El nombre no puede estar vacío") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") String nombre,

        @Schema(description = "Ubicación de la parada", example = "40.4168,-3.7038") @NotBlank(message = "La ubicación no puede estar vacía") @Pattern(regexp = "^-?\\d{1,3}\\.\\d+,-?\\d{1,3}\\.\\d+$", message = "La ubicación debe tener formato 'latitud,longitud' (ej: 40.4168,-3.7038)") String ubicacion,

        @Schema(description = "Descripción de la parada", example = "Parada junto a la catedral") @NotBlank(message = "La descripción no puede estar vacía") @Size(max = 255, message = "La descripción no puede superar los 255 caracteres") String descripcion

) {
    public static CreateUpdateParadaDTO fromEntity(Parada parada) {
        return new CreateUpdateParadaDTO(
                parada.getNombre(),
                parada.getUbicacion(),
                parada.getDescripcion());
    }
}
