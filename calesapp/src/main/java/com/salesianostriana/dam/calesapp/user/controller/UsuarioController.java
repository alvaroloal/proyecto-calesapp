package com.salesianostriana.dam.calesapp.user.controller;

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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

}
