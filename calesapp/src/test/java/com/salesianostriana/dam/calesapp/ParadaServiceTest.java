package com.salesianostriana.dam.calesapp;
import com.salesianostriana.dam.calesapp.dto.parada.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.repository.CiudadRepository;
import com.salesianostriana.dam.calesapp.repository.ParadaRepository;
import com.salesianostriana.dam.calesapp.service.ParadaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @Mock
    private CiudadRepository ciudadRepository;

    @InjectMocks
    private ParadaService paradaService;

    private Parada parada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parada = new Parada();
        parada.setId(1L);
        parada.setNombre("Parada Centro");
        parada.setDescripcion("Parada en el centro de la ciudad");
        parada.setUbicacion("Calle Mayor 123");
    }

    @Test
    void testFindAll() {
        when(paradaRepository.findAll()).thenReturn(Arrays.asList(parada));

        List<Parada> resultado = paradaService.findAll();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(paradaRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Existe() {
        when(paradaRepository.existsById(1L)).thenReturn(true);
        when(paradaRepository.findById(1L)).thenReturn(Optional.of(parada));

        Optional<Parada> resultado = paradaService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Parada Centro", resultado.get().getNombre());
        verify(paradaRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NoExiste() {
        when(paradaRepository.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> paradaService.findById(1L));
        assertEquals("Parada no encontrada", exception.getMessage());
        verify(paradaRepository, never()).findById(anyLong());
    }

    @Test
    void testCreate() {
        CreateUpdateParadaDTO dto = new CreateUpdateParadaDTO("Nueva Parada", "Descripción", "Ubicación");

        when(paradaRepository.save(any(Parada.class))).thenReturn(parada);

        Parada resultado = paradaService.create(dto);

        assertNotNull(resultado);
        assertEquals("Parada Centro", resultado.getNombre());
        verify(paradaRepository, times(1)).save(any(Parada.class));
    }

    @Test
    void testUpdate_Existe() {
        CreateUpdateParadaDTO dto = new CreateUpdateParadaDTO("Parada Actualizada", "Descripción Actualizada", "Ubicación Nueva");

        when(paradaRepository.existsById(1L)).thenReturn(true);
        when(paradaRepository.findById(1L)).thenReturn(Optional.of(parada));
        when(paradaRepository.save(any(Parada.class))).thenReturn(parada);

        Optional<Parada> resultado = paradaService.update(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Parada Actualizada", resultado.get().getNombre());
        verify(paradaRepository, times(1)).save(any(Parada.class));
    }

    @Test
    void testUpdate_NoExiste() {
        CreateUpdateParadaDTO dto = new CreateUpdateParadaDTO("Parada Actualizada", "Descripción Actualizada", "Ubicación Nueva");

        when(paradaRepository.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> paradaService.update(1L, dto));
        assertEquals("Parada no encontrada", exception.getMessage());
        verify(paradaRepository, never()).save(any(Parada.class));
    }

    @Test
    void testDelete_Existe() {
        when(paradaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(paradaRepository).deleteById(1L);

        Boolean resultado = paradaService.delete(1L);

        assertTrue(resultado);
        verify(paradaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NoExiste() {
        when(paradaRepository.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> paradaService.delete(1L));
        assertEquals("Parada no encontrada", exception.getMessage());
        verify(paradaRepository, never()).deleteById(anyLong());
    }
}

