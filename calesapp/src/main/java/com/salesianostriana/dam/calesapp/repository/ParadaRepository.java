package com.salesianostriana.dam.calesapp.repository;

import com.salesianostriana.dam.calesapp.model.Parada;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ParadaRepository extends JpaRepository<Parada, Long>, JpaSpecificationExecutor<Parada> {

    List<Parada> findByNombre(String nombre);

}
