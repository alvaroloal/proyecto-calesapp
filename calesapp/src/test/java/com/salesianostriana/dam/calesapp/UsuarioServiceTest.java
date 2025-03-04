package com.salesianostriana.dam.calesapp;

import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.user.dto.CreateUsuarioRequest;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.model.UsuarioRole;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import com.salesianostriana.dam.calesapp.user.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private CreateUsuarioRequest createUsuarioRequest;
    private UUID userId;

    /*@BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        usuario = new Usuario();
        usuario.setId(userId);
        usuario.setUsername("testUser");
        usuario.setPassword("encodedPassword");
        usuario.setRol(UsuarioRole.USER);
        usuario.setEnabled(true);

        createUsuarioRequest = new CreateUsuarioRequest(
                "testUser",
                "plainPassword",
                UsuarioRole.USER
        );
    }*/


    @Test
    void testCreateUser() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.createUser(createUsuarioRequest);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(UsuarioRole.USER, result.getRol());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> result = usuarioService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(usuarioRepository.existsById(userId)).thenReturn(true);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(usuario.getUsername(), result.get().getUsername());
        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_NotFound() {
        when(usuarioRepository.existsById(userId)).thenReturn(false);

        assertThrows(CustomException.class, () -> usuarioService.findById(userId));
    }

    @Test
    void testUpdateUser() {
        when(usuarioRepository.existsById(userId)).thenReturn(true);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Optional<Usuario> result = usuarioService.update(userId, createUsuarioRequest);

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
        assertEquals("plainPassword", result.get().getPassword());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        when(usuarioRepository.existsById(userId)).thenReturn(false);

        assertThrows(CustomException.class, () -> usuarioService.update(userId, createUsuarioRequest));
    }

    @Test
    void testDeleteUser() {
        when(usuarioRepository.existsById(userId)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(userId);

        boolean result = usuarioService.delete(userId);

        assertTrue(result);
        verify(usuarioRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(usuarioRepository.existsById(userId)).thenReturn(false);

        assertThrows(CustomException.class, () -> usuarioService.delete(userId));
    }
}
