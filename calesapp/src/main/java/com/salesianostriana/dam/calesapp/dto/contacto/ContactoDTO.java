package com.salesianostriana.dam.calesapp.dto.contacto;

import com.salesianostriana.dam.calesapp.model.Contacto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Datos de un contacto")
public record ContactoDTO(
        @Schema(description = "ID del contacto", example = "1") Long id,

        @Schema(description = "Mensaje de contacto", example = "Email") String mensaje,

        @Schema(description = "Fecha del contacto", example = "2024-01-15") LocalDate fecha,

        @Schema(description = "ID del servicio asociado", example = "1") Long servicioId,

        @Schema(description = "ID de la parada asociada", example = "1") Long paradaId,

        @Schema(description = "ID del cochero contactado", example = "1") Long cocheroId) {

    public static ContactoDTO fromEntity(Contacto contacto) {
        return new ContactoDTO(
                contacto.getId(),
                contacto.getMensaje(),
                contacto.getFecha(),
                contacto.getServicio().getId(),
                contacto.getParada().getId(),
                contacto.getCochero().getId());
    }
}
