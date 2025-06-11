package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.parada.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.repository.CiudadRepository;
import com.salesianostriana.dam.calesapp.repository.ParadaRepository;
import com.salesianostriana.dam.calesapp.user.query.ParadaSpecificationBuilder;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.salesianostriana.dam.calesapp.specification.SearchCriteria;
import com.salesianostriana.dam.calesapp.specification.parada.ParadaSpecification;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParadaService {

    private final ParadaRepository paradaRepository;

    private final CiudadRepository ciudadRepository;

    public ParadaService(ParadaRepository paradaRepository, CiudadRepository ciudadRepository) {
        this.paradaRepository = paradaRepository;
        this.ciudadRepository = ciudadRepository;
    }

    public Page<Parada> findAll(Pageable pageable) {
        try {
            return paradaRepository.findAll(pageable);
        } catch (Exception e) {
            throw new CustomException("Error al buscar paradas paginadas");
        }
    }

    public List<Parada> findAll() {
        try {
            return paradaRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar paradas");
        }
    }

    public Optional<Parada> findById(Long id) {
        if (!paradaRepository.existsById(id)) {
            throw new CustomException("Parada no encontrada");
        }
        try {
            return paradaRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar parada");
        }
    }

    public Parada create(CreateUpdateParadaDTO paradaDTO) {
        try {
            Parada parada = new Parada();
            return updateParadaFromDTO(parada, paradaDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear parada");
        }
    }

    public Optional<Parada> update(Long id, CreateUpdateParadaDTO paradaDTO) {
        if (!paradaRepository.existsById(id)) {
            throw new CustomException("Parada no encontrada");
        }
        try {
            return paradaRepository.findById(id)
                    .map(parada -> updateParadaFromDTO(parada, paradaDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar parada");
        }
    }

    public Boolean delete(Long id) {
        if (!paradaRepository.existsById(id)) {
            throw new CustomException("Parada no encontrada");
        }
        try {
            paradaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar parada");
        }
    }

    private Parada updateParadaFromDTO(Parada parada, CreateUpdateParadaDTO paradaDTO) {
        parada.setNombre(paradaDTO.nombre());
        parada.setDescripcion(paradaDTO.descripcion());
        parada.setUbicacion(paradaDTO.ubicacion());
        parada.setCiudad(this.ciudadRepository.findByNombre("Sevilla"));
        System.out.println(parada.toString());
        return paradaRepository.save(parada);
    }

    // search sin paginacion
    // public List<Parada> search(List<SearchCriteria> searchCriteriaList) {
    // ParadaSpecificationBuilder paradaSpecificationBuilder = new
    // ParadaSpecificationBuilder(searchCriteriaList);
    // Specification<Parada> where = paradaSpecificationBuilder.build();
    // return paradaRepository.findAll(where);
    // }

    // public Page<Parada> search(List<SearchCriteria> searchCriteriaList, Pageable
    // pageable) {
    // ParadaSpecificationBuilder paradaSpecificationBuilder = new
    // ParadaSpecificationBuilder(searchCriteriaList);
    // Specification<Parada> where = paradaSpecificationBuilder.build();
    // return paradaRepository.findAll(where, pageable);
    // }

    public List<Parada> searchByNombre(String nombre) {
        return paradaRepository.findByNombre(nombre);
    }

    public Page<Parada> search(List<SearchCriteria> criteria, Pageable pageable) {
        Specification<Parada> spec = ParadaSpecification.withCriteria(criteria);
        return paradaRepository.findAll(spec, pageable);
    }

}
