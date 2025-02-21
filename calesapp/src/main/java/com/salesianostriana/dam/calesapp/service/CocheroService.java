package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.cochero.CreateUpdateCocheroDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.repository.CocheroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CocheroService {

    private final CocheroRepository cocheroRepository;

    public CocheroService(CocheroRepository cocheroRepository) {
        this.cocheroRepository = cocheroRepository;
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
        cochero.setExperiencia(cocheroDTO.experiencia());
        return cocheroRepository.save(cochero);
    }
}
