package com.salesianostriana.dam.calesapp.security.jwt.verification;


import com.salesianostriana.dam.calesapp.user.dto.UserResponse;
import com.salesianostriana.dam.calesapp.user.model.User;
import com.salesianostriana.dam.calesapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${jwt.verification.duration}")
    private int durationInMinutes;
    private final UserRepository userRepository;

    public VerificationToken createToken(User user) {
        verificationTokenRepository.deleteByUser(user);
        return verificationTokenRepository.save(
                VerificationToken.builder()
                        .user(user)
                        .expireAt(Instant.now().plusSeconds(durationInMinutes * 60))
                        .build()
        );
    }

    public VerificationToken verifyToken(VerificationToken token) {

        if(token.getExpireAt().compareTo(Instant.now()) < 0) {
            verificationTokenRepository.delete(token);
            throw new VerificationTokenException("Token de verificación caducado, vuelve a solicitar la verificación");
        }

        return token;
    }

    public User verifyUser(String token) {

        return verificationTokenRepository.findById(UUID.fromString(token))
                .map(this::verifyToken)
                .map(VerificationToken::getUser)
                .map(usuario -> {

                    usuario.setEnabled(true);
                    verificationTokenRepository.deleteByUser(usuario);
                    return userRepository.save(usuario);
                })
                .orElseThrow(() -> new VerificationTokenException("No se ha encontrado el token"));
    }

    public UserResponse refreshToken(String token) {

        return verificationTokenRepository.findById(UUID.fromString(token))
                .map(VerificationToken::getUser)
                .map(user -> {

                    VerificationToken verificationToken = this.createToken(user);

                    return UserResponse.of(user, verificationToken.getToken());
                })
                .orElseThrow(() -> new VerificationTokenException("No se ha encontrado el token"));
    }
}


