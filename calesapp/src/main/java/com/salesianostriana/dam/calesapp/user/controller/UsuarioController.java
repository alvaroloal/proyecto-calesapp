package com.salesianostriana.dam.calesapp.user.controller;

import com.salesianostriana.dam.calesapp.dto.contacto.ContactoDTO;
import com.salesianostriana.dam.calesapp.security.jwt.access.JwtService;
import com.salesianostriana.dam.calesapp.security.jwt.refresh.RefreshToken;
import com.salesianostriana.dam.calesapp.security.jwt.refresh.RefreshTokenRequest;
import com.salesianostriana.dam.calesapp.security.jwt.refresh.RefreshTokenService;
import com.salesianostriana.dam.calesapp.security.jwt.verification.VerificationToken;
import com.salesianostriana.dam.calesapp.security.jwt.verification.VerificationTokenService;
import com.salesianostriana.dam.calesapp.user.dto.CreateUsuarioRequest;
import com.salesianostriana.dam.calesapp.user.dto.LoginRequest;
import com.salesianostriana.dam.calesapp.user.dto.UsuarioResponse;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import com.salesianostriana.dam.calesapp.user.service.UsuarioService;
import com.salesianostriana.dam.calesapp.user.util.SearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log
@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final VerificationTokenService verificationTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody CreateUsuarioRequest createUsuarioRequest) {
        Usuario user = usuarioService.createUser(createUsuarioRequest);

        VerificationToken verificationToken = verificationTokenService.createToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioResponse.of(user, verificationToken.getToken()));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.username(),
                                loginRequest.password()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario user = (Usuario) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioResponse.of(user, accessToken, refreshToken.getToken()));
    }

    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));

    }

    @GetMapping("/me")
    public UsuarioResponse me(@AuthenticationPrincipal Usuario user) {
        return UsuarioResponse.of(user);
    }

    @GetMapping("/me/admin")
    public Usuario adminMe(@AuthenticationPrincipal Usuario user) {
        System.out.println("prueba");
        return user;
    }

    @PutMapping("/auth/user/verify")
    public UsuarioResponse verifyUser(@RequestParam String token) {
        return UsuarioResponse.of(verificationTokenService.verifyUser(token));
    }

    @PostMapping("/auth/user/verify/refresh")
    public UsuarioResponse refreshVerification(@RequestParam String token) {
        return verificationTokenService.refreshToken(token);
    }

    @GetMapping("/api/usuarios")
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<UsuarioResponse> usuarioResponse = usuarioService.findAll().stream()
                .map(UsuarioResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarioResponse);
    }

    @GetMapping("/api/usuarios/{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Retorna un usuario basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioResponse> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar", required = true)
            @PathVariable("id") UUID id) {
        UsuarioResponse usuario = UsuarioResponse.of(usuarioService.findById(id).get());
        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/api/usuarios/{id}")
    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza un usuario existente basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UsuarioResponse> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @RequestBody CreateUsuarioRequest usuarioDTO) {
        return usuarioService.update(id, usuarioDTO)
                .map(usuario -> ResponseEntity.ok(UsuarioResponse.of(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable("id") UUID id) {
        if (usuarioService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/api/usuarios/buscar")
    public List<Usuario> buscar(@RequestParam String search) {
        log.info(search);
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                log.info(matcher.group(1));
                log.info(matcher.group(2));
                log.info(matcher.group(3));
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return usuarioService.search(params);
    }




}
