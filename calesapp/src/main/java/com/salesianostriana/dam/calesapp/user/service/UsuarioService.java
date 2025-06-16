package com.salesianostriana.dam.calesapp.user.service;

import com.salesianostriana.dam.calesapp.error.CustomException;
import com.salesianostriana.dam.calesapp.user.dto.CreateUsuarioRequest;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.model.UsuarioRole;
import com.salesianostriana.dam.calesapp.user.query.UserSpecificationBuilder;
import com.salesianostriana.dam.calesapp.user.repository.UsuarioRepository;
import com.salesianostriana.dam.calesapp.user.util.SearchCriteria;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario createUser(CreateUsuarioRequest createUsuarioRequest) {
        Usuario usuario = new Usuario();
        if (createUsuarioRequest.rol().equals(UsuarioRole.ROLE_USER))
            usuario.setRol(UsuarioRole.valueOf(UsuarioRole.ROLE_USER.name()));
        else {
            usuario.setRol(UsuarioRole.valueOf(UsuarioRole.ROLE_ADMIN.name()));
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
            Optional<Usuario> userOpt = usuarioRepository.findById(id);
            if (userOpt.isPresent()) {
                Usuario user = userOpt.get();
                user.getValoraciones().clear();
                user.getContactos().clear();
                usuarioRepository.delete(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new CustomException("Error al eliminar usuario");
        }
    }

    public List<Usuario> search(List<SearchCriteria> searchCriteriaList) {
        UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder(searchCriteriaList);
        Specification<Usuario> where = userSpecificationBuilder.build();
        return usuarioRepository.findAll(where);
    }

    public Optional<Usuario> bloquearUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            usuario.setEnabled(false);
            usuarioRepository.save(usuario);
            return Optional.of(usuario);
        } catch (Exception e) {
            throw new CustomException("Error al bloquear usuario");
        }
    }

    public Optional<Usuario> desbloquearUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new CustomException("Usuario no encontrado");
        }
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            usuario.setEnabled(true);
            usuarioRepository.save(usuario);
            return Optional.of(usuario);
        } catch (Exception e) {
            throw new CustomException("Error al desbloquear usuario");
        }
    }
}