package com.salesianostriana.dam.calesapp.dto.contacto;

import com.salesianostriana.dam.calesapp.model.Contacto;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Schema(description = "Datos de un contacto")
public record CreateUpdateContactoDTO(

        @NotBlank(message = "El mensaje no puede estar vacío") @Size(max = 1000, message = "El mensaje no puede superar los 1000 caracteres") @Schema(description = "Mensaje de contacto", example = "Mensaje de contacto") String mensaje,

        @NotNull(message = "La fecha es obligatoria") @PastOrPresent(message = "La fecha no puede ser futura") @Schema(description = "Fecha del contacto", example = "2024-01-15") LocalDate fecha,

        @NotNull(message = "El id del servicio es obligatorio") @Schema(description = "ID del servicio", example = "1") Long servicioId) {

    public static CreateUpdateContactoDTO fromEntity(Contacto contacto) {
        return new CreateUpdateContactoDTO(
                contacto.getMensaje(),
                contacto.getFecha(),
                contacto.getServicio().getId());
    }
}
