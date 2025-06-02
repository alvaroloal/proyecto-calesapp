package com.salesianostriana.dam.calesapp.security.jwt.verification;



import com.salesianostriana.dam.calesapp.user.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

    @Modifying
    @Transactional
    void deleteByUser(Usuario user);
}

