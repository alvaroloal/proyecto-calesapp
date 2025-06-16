package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.valoracion.CreateUpdateValoracionDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.model.Valoracion;
import com.salesianostriana.dam.calesapp.repository.ServicioRepository;
import com.salesianostriana.dam.calesapp.repository.ValoracionRepository;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ValoracionService {
    private final ValoracionRepository valoracionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;

    public ValoracionService(ValoracionRepository valoracionRepository, UsuarioRepository usuarioRepository,
            ServicioRepository servicioRepository) {
        this.valoracionRepository = valoracionRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
    }

    public List<Valoracion> findAll() {
        try {
            return valoracionRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar valoración");
        }
    }

    public Optional<Valoracion> findById(Long id) {
        try {
            return valoracionRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar valoracion");
        }
    }

    public Valoracion create(CreateUpdateValoracionDTO valoracionDTO) {
        try {
            Valoracion valoracion = new Valoracion();
            return updateValoracionFromDTO(valoracion, valoracionDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear valoracion");
        }
    }

    public Optional<Valoracion> update(Long id, CreateUpdateValoracionDTO valoracionDTO) {
        if (!valoracionRepository.existsById(id)) {
            throw new CustomException("Valoracion no encontrada");
        }
        try {
            return valoracionRepository.findById(id)
                    .map(valoracion -> updateValoracionFromDTO(valoracion, valoracionDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar valoracion");
        }
    }

    public Boolean delete(Long id) {
        if (!valoracionRepository.existsById(id)) {
            throw new CustomException("Valoracion no encontrada");
        }
        try {
            Optional<Valoracion> valoracionOpt = valoracionRepository.findById(id);
            if (valoracionOpt.isPresent()) {
                Valoracion valoracion = valoracionOpt.get();
                valoracion.setUsuario(null);
                valoracion.setServicio(null);
                valoracionRepository.delete(valoracion);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar valoración");
        }
    }

    private Valoracion updateValoracionFromDTO(Valoracion valoracion, CreateUpdateValoracionDTO valoracionDTO) {
        valoracion.setPuntuacion(valoracionDTO.puntuacion());
        valoracion.setComentario(valoracionDTO.comentario());
        valoracion.setFecha(valoracionDTO.fecha());
        Usuario usuario = usuarioRepository.findById(valoracionDTO.usuarioId()).get();
        valoracion.setUsuario(usuario);
        Servicio servicio = servicioRepository.findById(valoracionDTO.servicioId()).get();
        valoracion.setServicio(servicio);
        return valoracionRepository.save(valoracion);
    }
}