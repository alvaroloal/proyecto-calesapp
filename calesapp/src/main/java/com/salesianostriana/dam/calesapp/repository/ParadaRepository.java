package com.salesianostriana.dam.calesapp.repository;

import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ParadaRepository extends JpaRepository<Parada,Long>, JpaSpecificationExecutor<Parada> {

}
