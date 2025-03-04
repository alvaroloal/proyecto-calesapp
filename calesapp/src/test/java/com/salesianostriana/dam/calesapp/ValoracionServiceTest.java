package com.salesianostriana.dam.calesapp;

import com.salesianostriana.dam.calesapp.dto.valoracion.CreateUpdateValoracionDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.model.Valoracion;
import com.salesianostriana.dam.calesapp.repository.ServicioRepository;
import com.salesianostriana.dam.calesapp.repository.ValoracionRepository;
import com.salesianostriana.dam.calesapp.service.ValoracionService;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValoracionServiceTest {

    @Mock
    private ValoracionRepository valoracionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ValoracionService valoracionService;

    private Usuario usuario;
    private Servicio servicio;
    private Valoracion valoracion;
    private CreateUpdateValoracionDTO valoracionDTO;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .username("testUser")
                .password("password")
                .rol(com.salesianostriana.dam.calesapp.user.model.UsuarioRole.USER)
                .enabled(true)
                .build();

        servicio = new Servicio();
        servicio.setId(1L);

        valoracion = new Valoracion();
        valoracion.setId(1L);
        valoracion.setPuntuacion(5);
        valoracion.setComentario("Muy buen servicio");
        valoracion.setFecha(LocalDate.now());
        valoracion.setUsuario(usuario);
        valoracion.setServicio(servicio);

        valoracionDTO = new CreateUpdateValoracionDTO(5, "Muy buen servicio", LocalDate.now(), usuario.getId(), servicio.getId());
    }

    @Test
    void testFindAll() {
        when(valoracionRepository.findAll()).thenReturn(List.of(valoracion));

        List<Valoracion> result = valoracionService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(valoracionRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));

        Optional<Valoracion> result = valoracionService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(valoracion.getComentario(), result.get().getComentario());
        verify(valoracionRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> valoracionService.findById(1L));
    }

    @Test
    void testCreate() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(servicioRepository.findById(servicio.getId())).thenReturn(Optional.of(servicio));
        when(valoracionRepository.save(any(Valoracion.class))).thenReturn(valoracion);

        Valoracion result = valoracionService.create(valoracionDTO);

        assertNotNull(result);
        assertEquals(valoracion.getPuntuacion(), result.getPuntuacion());
        verify(valoracionRepository, times(1)).save(any(Valoracion.class));
    }

    @Test
    void testUpdate() {
        when(valoracionRepository.existsById(1L)).thenReturn(true);
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(servicioRepository.findById(servicio.getId())).thenReturn(Optional.of(servicio));
        when(valoracionRepository.save(any(Valoracion.class))).thenReturn(valoracion);

        Optional<Valoracion> result = valoracionService.update(1L, valoracionDTO);

        assertTrue(result.isPresent());
        assertEquals(valoracionDTO.comentario(), result.get().getComentario());
        verify(valoracionRepository, times(1)).save(any(Valoracion.class));
    }

    @Test
    void testUpdate_NotFound() {
        when(valoracionRepository.existsById(1L)).thenReturn(false);

        assertThrows(CustomException.class, () -> valoracionService.update(1L, valoracionDTO));
    }

    @Test
    void testDelete() {
        when(valoracionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(valoracionRepository).deleteById(1L);

        boolean result = valoracionService.delete(1L);

        assertTrue(result);
        verify(valoracionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(valoracionRepository.existsById(1L)).thenReturn(false);

        assertThrows(CustomException.class, () -> valoracionService.delete(1L));
    }
}

