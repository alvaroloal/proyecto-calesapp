package com.salesianostriana.dam.calesapp.user.model;

import com.salesianostriana.dam.calesapp.model.Contacto;
import com.salesianostriana.dam.calesapp.model.Valoracion;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // @NaturalId
    // @Column(name = "nombre_usuario", unique = true, updatable = false)
    private String username;

    private String password;

    // @ElementCollection(fetch = FetchType.EAGER)
    private UsuarioRole rol;

    private boolean enabled = true;

    private String verificationToken;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacto> contactos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Valoracion> valoraciones = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority("ROLE_" + rol));

        return list;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
