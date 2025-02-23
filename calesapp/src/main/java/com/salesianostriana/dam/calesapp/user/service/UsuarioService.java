package com.salesianostriana.dam.calesapp.user.service;

import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.user.dto.CreateUsuarioRequest;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.model.UsuarioRole;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public Usuario createUser(CreateUsuarioRequest createUsuarioRequest) {
        Usuario user = Usuario.builder()
                .username(createUsuarioRequest.username())
                .password(passwordEncoder.encode(createUsuarioRequest.password()))
                .roles(Set.of(UsuarioRole.USER))
                .build();
        return usuarioRepository.save(user);
    }

    public Usuario createUserAdmin(CreateUsuarioRequest createUsuarioRequest) {
        Usuario user = Usuario.builder()
                .username(createUsuarioRequest.username())
                .password(passwordEncoder.encode(createUsuarioRequest.password()))
                .roles(Set.of(UsuarioRole.ADMIN))
                .build();
        return usuarioRepository.save(user);
    }

    public List<Usuario> findAll() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error al buscar usuarios");
        }
    }

    public Optional<Usuario> findById(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            return usuarioRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException("Error al buscar usuario");
        }
    }



}
