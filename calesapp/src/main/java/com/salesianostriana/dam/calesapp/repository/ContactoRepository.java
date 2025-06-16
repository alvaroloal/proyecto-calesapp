package com.salesianostriana.dam.calesapp.repository;

import com.salesianostriana.dam.calesapp.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    @Query("SELECT c FROM Contacto c WHERE c.servicio.id = :servicioId")
    List<Contacto> findByServicioId(@Param("servicioId") Long servicioId);

    List<Contacto> findByUsuario_Id(UUID usuarioId);

}
