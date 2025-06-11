package com.salesianostriana.dam.calesapp.repository;

import com.salesianostriana.dam.calesapp.model.Cochero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CocheroRepository extends JpaRepository<Cochero, Long>, JpaSpecificationExecutor<Cochero> {

    List<Cochero> findByNombre(String nombre);
}
