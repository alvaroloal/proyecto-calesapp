package com.salesianostriana.dam.calesapp.dto.valoracion;

import com.salesianostriana.dam.calesapp.model.Valoracion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Datos de una valoracion")
public record ValoracionDTO(
        @Schema(description = "ID valoracion", example = "1") Long id,

        @Schema(description = "Puntuacion de la valoracion", example = "9") Integer puntuacion,

        @Schema(description = "Comentario de la valoración", example = "Servicio excelente y buen trato") String comentario,

        @Schema(description = "Fecha valoración", example = "2024-01-15") LocalDate fecha,

        @Schema(description = "ID del usuario asociado", example = "1") UUID usuarioId,

        @Schema(description = "ID del servicio asociado", example = "1") Long servicioId,

        String usuarioNombre,
        String servicioNombre) {

    public static ValoracionDTO fromEntity(Valoracion valoracion) {
        return new ValoracionDTO(
                valoracion.getId(),
                valoracion.getPuntuacion(),
                valoracion.getComentario(),
                valoracion.getFecha(),
                valoracion.getUsuario().getId(),
                valoracion.getServicio().getId(),
                valoracion.getUsuario().getUsername(),
                valoracion.getServicio().getTipoServicio().name());
    }
}
