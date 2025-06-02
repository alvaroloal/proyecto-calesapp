package com.salesianostriana.dam.calesapp.repository;

import com.salesianostriana.dam.calesapp.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository <Ciudad, Long>{
    Ciudad findByNombre(String ciudad);
}
