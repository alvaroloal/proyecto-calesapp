package com.salesianostriana.dam.calesapp.dto.servicio;

import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.model.TipoServicio;

import jakarta.validation.constraints.*;

public record CreateUpdateServicioDTO(

        @NotNull(message = "El tipo de servicio es obligatorio") TipoServicio tipoServicio,

        @NotNull(message = "La tarifa es obligatoria") @PositiveOrZero(message = "La tarifa debe ser un número positivo o cero") Double tarifa,

        @NotNull(message = "La duración es obligatoria") @Positive(message = "La duración debe ser un número positivo") Integer duracion,

        @NotBlank(message = "La descripción no puede estar vacía") @Size(max = 500, message = "La descripción no puede superar 500 caracteres") String descripcion,

        @NotNull(message = "La disponibilidad es obligatoria") Boolean disponibilidad) {

    public static CreateUpdateServicioDTO fromEntity(Servicio servicio) {
        return new CreateUpdateServicioDTO(
                servicio.getTipoServicio(),
                servicio.getTarifa(),
                servicio.getDuracion(),
                servicio.getDescripcion(),
                servicio.getDisponibilidad());
    }
}
