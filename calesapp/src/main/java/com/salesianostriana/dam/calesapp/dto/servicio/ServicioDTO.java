package com.salesianostriana.dam.calesapp.dto.servicio;

import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.model.TipoServicio;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Datos de un servicio")
public record ServicioDTO(
        @Schema(description = "ID del servicio", example = "1")
        Long id,

        @Schema(description = "Tipo de servicio", example = "PERSONALIZADO")
        TipoServicio tipoServicio,

        @Schema(description = "Tarifa del servicio", example = "60")
        Double tarifa,

        @Schema(description = "Duración del servicio", example = "45´")
        Integer duracion,

        @Schema(description = "Descripción del servicio", example = "Paseo por las zonas mas emblemáticas del centro")
        String descripcion,

        @Schema(description = "Disponibilidad del servicio", example = "true")
        Boolean disponibilidad

) {

    public static ServicioDTO fromEntity(Servicio servicio) {
        return new ServicioDTO(
                servicio.getId(),
                servicio.getTipoServicio(),
                servicio.getTarifa(),
                servicio.getDuracion(),
                servicio.getDescripcion(),
                servicio.getDisponibilidad()

        );
    }

    public static ServicioDTO of(Servicio servicio) {
        return new ServicioDTO(servicio.getId(), servicio.getTipoServicio(), servicio.getTarifa(), servicio.getDuracion(), servicio.getDescripcion(), servicio.getDisponibilidad());
    }
}
