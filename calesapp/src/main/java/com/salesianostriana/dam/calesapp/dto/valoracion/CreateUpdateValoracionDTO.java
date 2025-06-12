package com.salesianostriana.dam.calesapp.dto.valoracion;

import com.salesianostriana.dam.calesapp.model.Valoracion;
import com.salesianostriana.dam.calesapp.validation.annotations.FechaPasada;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Datos de una valoración")
public record CreateUpdateValoracionDTO(

        @NotNull(message = "La puntuación es obligatoria") @Min(value = 1, message = "La puntuación mínima es 1") @Max(value = 10, message = "La puntuación máxima es 10") @Schema(description = "Puntuacion de la valoración", example = "8") Integer puntuacion,

        @NotBlank(message = "El comentario no puede estar vacío") @Size(max = 500, message = "El comentario no puede superar los 500 caracteres") @Schema(description = "Comentario de la valoración", example = "Todo perfecto, repetiré sin duda") String comentario,

        @NotNull(message = "La fecha es obligatoria") @PastOrPresent(message = "La fecha no puede ser futura") @Schema(description = "Fecha de la valoracion", example = "2024-01-15") @FechaPasada(message = "La fecha de la valoración no puede ser futura") LocalDate fecha,

        @NotNull(message = "El id del usuario es obligatorio") @Schema(description = "ID del usuario", example = "550e8400-e29b-41d4-a716-446655440000") UUID usuarioId,

        @NotNull(message = "El id del servicio es obligatorio") @Schema(description = "ID del servicio", example = "1") Long servicioId

) {

    public static CreateUpdateValoracionDTO fromEntity(Valoracion valoracion) {
        return new CreateUpdateValoracionDTO(
                valoracion.getPuntuacion(),
                valoracion.getComentario(),
                valoracion.getFecha(),
                valoracion.getUsuario().getId(),
                valoracion.getServicio().getId());
    }
}
