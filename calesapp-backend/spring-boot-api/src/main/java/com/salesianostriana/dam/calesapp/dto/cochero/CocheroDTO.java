package com.salesianostriana.dam.calesapp.dto.cochero;

import com.salesianostriana.dam.calesapp.model.Cochero;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos del cochero")
public record CocheroDTO(
        @Schema(description = "ID del cochero", example = "1")
        Long id,

        @Schema(description = "Nombre del cochero", example = "Manuel")
        String nombre,

        @Schema(description = "Apellidos del cochero", example = "Lorente Cala")
        String apellidos,

        @Schema(description = "Experiencia en el sector", example = "10.0")
        Double experiencia

) {

    public static CocheroDTO fromEntity(Cochero cochero) {
        return new CocheroDTO(
                cochero.getId(),
                cochero.getNombre(),
                cochero.getApellidos(),
                cochero.getExperiencia()
        );
    }

    public static CocheroDTO of(Cochero cochero) {
        return new CocheroDTO(cochero.getId(), cochero.getNombre(), cochero.getApellidos(), cochero.getExperiencia());
    }
}
