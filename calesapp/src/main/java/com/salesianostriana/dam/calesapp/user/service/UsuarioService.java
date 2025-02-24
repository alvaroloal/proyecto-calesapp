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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public Usuario createUser(CreateUsuarioRequest createUsuarioRequest) {
        Usuario usuario = new Usuario();
        /*VALIDAR QUE EXISTA Y QUE EL NOMBRE DE USUARIO NO EXISTA*/
        if(createUsuarioRequest.rol().equals(UsuarioRole.USER))
            usuario.setRol(UsuarioRole.valueOf(UsuarioRole.USER.name()));
            else{
                usuario.setRol(UsuarioRole.valueOf(UsuarioRole.ADMIN.name()));
        }
            usuario.setUsername(createUsuarioRequest.username());
            usuario.setPassword(passwordEncoder.encode(createUsuarioRequest.password()));
        return usuarioRepository.save(usuario);
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

    public Optional<Usuario> update(UUID id, CreateUsuarioRequest createUsuarioRequest) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            usuario.setUsername(createUsuarioRequest.username());
            usuario.setPassword(createUsuarioRequest.password());
            usuario.setRol(createUsuarioRequest.rol());
            usuarioRepository.save(usuario);
            return Optional.of(usuario);
        } catch (Exception e) {
            throw new CustomException("Error al actualizar usuario");
        }

    }

    public Boolean delete(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar usuario");
        }
    }



}
