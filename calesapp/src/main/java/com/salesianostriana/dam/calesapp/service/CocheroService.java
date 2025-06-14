package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.cochero.CreateUpdateCocheroDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.repository.CocheroRepository;
import com.salesianostriana.dam.calesapp.specification.SearchCriteria;
import com.salesianostriana.dam.calesapp.specification.cochero.CocheroSpecification;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

@Service
@Transactional
public class CocheroService {

    private final CocheroRepository cocheroRepository;

    public CocheroService(CocheroRepository cocheroRepository) {
        this.cocheroRepository = cocheroRepository;
    }

    public Page<Cochero> findAll(Pageable pageable) {
        try {
            return cocheroRepository.findAll(pageable);
        } catch (Exception e) {
            throw new CustomException("Error al buscar cocheros paginados");
        }
    }

    public List<Cochero> findAll() {
        try {
            return cocheroRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar cocheros");
        }
    }

    public Optional<Cochero> findById(Long id) {
        if (!cocheroRepository.existsById(id)) {
            throw new CustomException("Cochero no encontrado");
        }
        try {
            return cocheroRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar cochero");
        }
    }

    public Cochero create(CreateUpdateCocheroDTO cocheroDTO) {
        try {
            Cochero cochero = new Cochero();
            return updateCocheroFromDTO(cochero, cocheroDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear cochero");
        }
    }

    public Optional<Cochero> update(Long id, CreateUpdateCocheroDTO cocheroDTO) {
        if (!cocheroRepository.existsById(id)) {
            throw new CustomException("Cochero no encontrado");
        }
        try {
            return cocheroRepository.findById(id)
                    .map(cochero -> updateCocheroFromDTO(cochero, cocheroDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar cochero");
        }
    }

    public Boolean delete(Long id) {
        if (!cocheroRepository.existsById(id)) {
            throw new CustomException("Cochero no encontrada");
        }
        try {
            cocheroRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar cochero");
        }
    }

    private Cochero updateCocheroFromDTO(Cochero cochero, CreateUpdateCocheroDTO cocheroDTO) {

        cochero.setNombre(cocheroDTO.nombre());
        cochero.setApellidos(cocheroDTO.apellidos());
        cochero.setMediaValoracion(cocheroDTO.mediaValoracion());
        return cocheroRepository.save(cochero);
    }

    public Page<Cochero> search(List<SearchCriteria> params, Pageable pageable) {
        Specification<Cochero> spec = Specification.where(null);
        for (SearchCriteria param : params) {
            spec = spec.and(new CocheroSpecification(param));
        }
        return cocheroRepository.findAll(spec, pageable);
    }

    public List<Cochero> searchByNombre(String nombre) {
        try {
            return cocheroRepository.findByNombre(nombre);
        } catch (Exception e) {
            throw new CustomException("Error al buscar cocheros por nombre");
        }
    }

}
