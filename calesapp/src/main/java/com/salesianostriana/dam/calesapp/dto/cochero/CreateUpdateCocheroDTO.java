package com.salesianostriana.dam.calesapp.dto.cochero;

import com.salesianostriana.dam.calesapp.model.Cochero;
import jakarta.validation.constraints.*;

public record CreateUpdateCocheroDTO(

        @NotBlank(message = "El nombre no puede estar vacío") @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres") String nombre,

        @NotBlank(message = "Los apellidos no pueden estar vacíos") @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres") String apellidos,

        @NotNull(message = "La experiencia es obligatoria") @PositiveOrZero(message = "La experiencia debe ser un número positivo o cero") Double experiencia

) {
    public static CreateUpdateCocheroDTO fromEntity(Cochero cochero) {
        return new CreateUpdateCocheroDTO(
                cochero.getNombre(),
                cochero.getApellidos(),
                cochero.getExperiencia());
    }
}
