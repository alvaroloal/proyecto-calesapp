package com.salesianostriana.dam.calesapp.dto.contacto;

import com.salesianostriana.dam.calesapp.model.Contacto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Datos de un contacto")
public record CreateUpdateContactoDTO(

        @Schema(description = "Mensaje de contacto", example = "Mensaje de contacto")
        String mensaje,

        @Schema(description = "Fecha del contacto", example = "2024-01-15")
        LocalDate fecha,

        @Schema(description = "ID del servicio", example = "1")
        Long servicioId
) {

    public static CreateUpdateContactoDTO fromEntity(Contacto contacto) {
        return new CreateUpdateContactoDTO(
                contacto.getMensaje(),
                contacto.getFecha(),
                contacto.getServicio().getId()
        );
    }
}
