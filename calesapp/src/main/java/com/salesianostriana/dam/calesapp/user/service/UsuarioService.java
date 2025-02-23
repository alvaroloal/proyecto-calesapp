package com.salesianostriana.dam.calesapp.user.service;

import com.salesianostriana.dam.calesapp.user.dto.CreateUsuarioRequest;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.model.UsuarioRole;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

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


}
