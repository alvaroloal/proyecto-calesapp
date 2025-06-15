package com.salesianostriana.dam.calesapp.service;

import com.salesianostriana.dam.calesapp.dto.contacto.CreateUpdateContactoDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.model.Contacto;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.repository.CocheroRepository;
import com.salesianostriana.dam.calesapp.repository.ContactoRepository;
import com.salesianostriana.dam.calesapp.repository.ParadaRepository;
import com.salesianostriana.dam.calesapp.repository.ServicioRepository;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParadaRepository paradaRepository;
    private final CocheroRepository cocheroRepository;

    public ContactoService(ContactoRepository contactoRepository, ServicioRepository servicioRepository,
            UsuarioRepository usuarioRepository, ParadaRepository paradaRepository,
            CocheroRepository cocheroRepository) {
        this.contactoRepository = contactoRepository;
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.paradaRepository = paradaRepository;
        this.cocheroRepository = cocheroRepository;

    }

    public List<Contacto> findAll() {
        try {
            return contactoRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar contactos");
        }
    }

    public Optional<Contacto> findById(Long id) {
        try {
            return contactoRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar contacto");
        }
    }

    public Contacto create(CreateUpdateContactoDTO contactoDTO) {
        try {
            Contacto contacto = new Contacto();
            System.out.println(contactoDTO);
            return updateContactoFromDTO(contacto, contactoDTO);
        } catch (Exception e) {
            throw new CustomException("Error al crear contacto");
        }
    }

    public Optional<Contacto> update(Long id, CreateUpdateContactoDTO contactoDTO) {
        if (!contactoRepository.existsById(id)) {
            throw new CustomException("Contacto no encontrado");
        }
        try {
            return contactoRepository.findById(id)
                    .map(contacto -> updateContactoFromDTO(contacto, contactoDTO));
        } catch (Exception e) {
            throw new CustomException("Error al actualizar contacto");
        }
    }

    public Boolean delete(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new CustomException("Contacto no encontrado");
        }
        try {
            contactoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar contacto");
        }
    }

    public List<Contacto> findByServicioId(Long servicioId) {
        if (!servicioRepository.existsById(servicioId)) {
            throw new CustomException("Servicio no encontrado");
        }
        try {
            return contactoRepository.findByServicioId(servicioId);
        } catch (Exception e) {
            throw new CustomException("Error al buscar contactos del servicio");
        }
    }

    private Contacto updateContactoFromDTO(Contacto contacto, CreateUpdateContactoDTO contactoDTO) {
        contacto.setMensaje(contactoDTO.mensaje());
        contacto.setFecha(contactoDTO.fecha());
        Servicio servicio = servicioRepository.findById(contactoDTO.servicioId()).get();
        contacto.setServicio(servicio);
        Usuario usuario = usuarioRepository.findById(contactoDTO.usuarioId()).get();
        contacto.setUsuario(usuario);

        Parada parada = paradaRepository.findById(contactoDTO.paradaId()).get();
        contacto.setParada(parada);

        Cochero cochero = cocheroRepository.findById(contactoDTO.cocheroId()).get();
        contacto.setCochero(cochero);

        return contactoRepository.save(contacto);
    }

}
