package com.salesianostriana.dam.calesapp.dto.valoracion;

import com.salesianostriana.dam.calesapp.model.Valoracion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Datos de una valoración")
public record CreateUpdateValoracionDTO(

        @Schema(description = "Puntuacion de la valoración", example = "8")
        Integer puntuacion,

        @Schema(description = "Comentario de la valoración", example = "Todo perfecto, repetiré sin duda")
        String comentario,

        @Schema(description = "Fecha de la valoracion", example = "2024-01-15")
        LocalDate fecha,

        @Schema(description = "ID del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID usuarioId,

        @Schema(description = "ID del servicio", example = "1")
        Long servicioId

) {

    public static CreateUpdateValoracionDTO fromEntity(Valoracion valoracion) {
        return new CreateUpdateValoracionDTO(

                valoracion.getPuntuacion(),
                valoracion.getComentario(),
                valoracion.getFecha(),
                valoracion.getUsuario().getId(),
                valoracion.getServicio().getId()

        );
    }
}
