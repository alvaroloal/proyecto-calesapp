package com.salesianostriana.dam.calesapp.dto.servicio;

import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.model.TipoServicio;


public record CreateUpdateServicioDTO(

        TipoServicio tipoServicio,
        Double tarifa,
        Integer duracion,
        String descripcion,
        Boolean disponibilidad
) {


    public static CreateUpdateServicioDTO fromEntity(Servicio servicio) {
        return new CreateUpdateServicioDTO(

                servicio.getTipoServicio(),
                servicio.getTarifa(),
                servicio.getDuracion(),
                servicio.getDescripcion(),
                servicio.getDisponibilidad()
        );
    }
}
