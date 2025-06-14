package com.salesianostriana.dam.calesapp.unit;

//import com.salesianostriana.dam.calesapp.dto.parada.CreateUpdateParadaDTO;
import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.repository.CiudadRepository;
import com.salesianostriana.dam.calesapp.repository.ParadaRepository;
import com.salesianostriana.dam.calesapp.service.ParadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @Mock
    private CiudadRepository ciudadRepository;

    private ParadaService paradaService;

    private Parada parada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paradaService = new ParadaService(paradaRepository, ciudadRepository);
        parada = new Parada();
        parada.setId(1L);
        parada.setNombre("Torre del Oro");
        parada.setDescripcion("Parada en el centro del paseo Colón");
        parada.setLat(37.3893);
        parada.setLng(-5.9961);
    }

    @Test
    void testFindAll() {
        when(paradaRepository.findAll()).thenReturn(Arrays.asList(parada));
        List<Parada> resultado = paradaService.findAll();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testFindById_Existe() {
        when(paradaRepository.findById(1L)).thenReturn(Optional.of(parada));// emular comportamiento del servicio
        when(paradaRepository.existsById(1L)).thenReturn(true);
        Optional<Parada> resultado = paradaService.findById(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Torre del Oro", resultado.get().getNombre());
    }

    @Test
    void testFindById_NoExiste() {
        when(paradaRepository.existsById(1L)).thenThrow(new CustomException("Parada no encontrada"));
        CustomException exception = assertThrows(CustomException.class, () -> paradaService.findById(1L));
        assertEquals("Parada no encontrada", exception.getMessage());
    }

    // @Test
    // void testCreate() {
    // CreateUpdateParadaDTO dto = new CreateUpdateParadaDTO("Nueva Parada",
    // "Descripción", "Ubicación");
    // when(paradaRepository.save(any(Parada.class))).thenReturn(parada);
    // Parada resultado = paradaService.create(dto);
    // assertNotNull(resultado);
    // assertEquals("Torre del Oro", resultado.getNombre()); // asegura que se
    // devuelve la entidad creada correctamente
    // }
    //
    // @Test
    // void testUpdate_Existe() {
    // CreateUpdateParadaDTO dto = new CreateUpdateParadaDTO("Parada actualizada",
    // "Descripción actualizada",
    // "Ubicación nueva");
    // when(paradaRepository.existsById(1L)).thenReturn(true);
    // when(paradaRepository.findById(1L)).thenReturn(Optional.of(parada));
    // when(paradaRepository.save(any(Parada.class))).thenReturn(parada);
    // Optional<Parada> resultado = paradaService.update(1L, dto);
    // assertTrue(resultado.isPresent());
    // assertEquals("Parada actualizada", resultado.get().getNombre());
    // }
    //
    @Test
    void testDelete_Existe() {
        when(paradaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(paradaRepository).deleteById(1L);
        Boolean resultado = paradaService.delete(1L);
        assertTrue(resultado);
        verify(paradaRepository, times(1)).deleteById(1L);
    }
}
