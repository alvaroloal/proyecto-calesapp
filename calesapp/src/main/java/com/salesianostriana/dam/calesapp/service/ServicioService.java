package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.servicio.CreateUpdateServicioDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.repository.ServicioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServicioService {
    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> findAll() {
        try {
            return servicioRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar servicios");
        }
    }

    public Optional<Servicio> findById(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new CustomException("Servicio no encontrado");
        }
        try {
            return servicioRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar servicio");
        }
    }

    public Servicio create(CreateUpdateServicioDTO servicioDTO) {
        try {
            Servicio servicio = new Servicio();
            return updateServicioFromDTO(servicio, servicioDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear servicio");
        }
    }

    public Optional<Servicio> update(Long id, CreateUpdateServicioDTO servicioDTO) {
        if (!servicioRepository.existsById(id)) {
            throw new CustomException("Servicio no encontrada");
        }
        try {
            return servicioRepository.findById(id)
                    .map(servicio -> updateServicioFromDTO(servicio, servicioDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar servicio");
        }
    }

    public Boolean delete(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new CustomException("Servicio no encontrada");
        }
        try {
            Optional<Servicio> servicioOpt = servicioRepository.findById(id);
            if (servicioOpt.isPresent()) {
                Servicio servicio = servicioOpt.get();
                servicio.getValoraciones().clear();
                servicio.getContactos().clear();
                servicioRepository.delete(servicio);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar servicio");
        }
    }

    private Servicio updateServicioFromDTO(Servicio servicio, CreateUpdateServicioDTO servicioDTO) {
        servicio.setTipoServicio(servicioDTO.tipoServicio());
        servicio.setTarifa(servicioDTO.tarifa());
        servicio.setDuracion(servicioDTO.duracion());
        servicio.setDescripcion(servicioDTO.descripcion());
        servicio.setDisponibilidad(servicioDTO.disponibilidad());
        return servicioRepository.save(servicio);
    }
}