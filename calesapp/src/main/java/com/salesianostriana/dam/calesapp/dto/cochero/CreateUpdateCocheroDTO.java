package com.salesianostriana.dam.calesapp.dto.cochero;

import com.salesianostriana.dam.calesapp.dto.parada.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.model.Parada;

public record CreateUpdateCocheroDTO(
        String nombre,
        String apellidos,
        Double experiencia
) {

    public static CreateUpdateCocheroDTO fromEntity(Cochero cochero) {
        return new CreateUpdateCocheroDTO(
                cochero.getNombre(),
                cochero.getApellidos(),
                cochero.getExperiencia()
        );
    }
}
