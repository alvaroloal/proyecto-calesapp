package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.repository.ParadaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ParadaService {

    private final ParadaRepository paradaRepository;

    public ParadaService(ParadaRepository paradaRepository) {
        this.paradaRepository = paradaRepository;
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
        return paradaRepository.save(parada);
    }
}
