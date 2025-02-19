package com.salesianostriana.dam.calesapp.user.repository;

import com.salesianostriana.dam.calesapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

}