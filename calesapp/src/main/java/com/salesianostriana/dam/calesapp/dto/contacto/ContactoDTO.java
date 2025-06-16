package com.salesianostriana.dam.calesapp.dto.contacto;

import com.salesianostriana.dam.calesapp.model.Contacto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Datos de un contacto")
public record ContactoDTO(

        @Schema(description = "ID del contacto", example = "1") Long id,

        @Schema(description = "Mensaje de contacto", example = "Mensaje de ejemplo") String mensaje,

        @Schema(description = "Fecha del contacto", example = "2024-01-15") LocalDate fecha,

        @Schema(description = "Nombre del servicio") String servicioNombre,

        @Schema(description = "Nombre de la parada") String paradaNombre,

        @Schema(description = "Nombre del cochero") String cocheroNombre

) {
    public static ContactoDTO fromEntity(Contacto contacto) {
        return new ContactoDTO(
                contacto.getId(),
                contacto.getMensaje(),
                contacto.getFecha(),
                contacto.getServicio().getTipoServicio().name(),
                contacto.getParada().getNombre(),
                contacto.getCochero().getNombre() + " " + contacto.getCochero().getApellidos());
    }
}
