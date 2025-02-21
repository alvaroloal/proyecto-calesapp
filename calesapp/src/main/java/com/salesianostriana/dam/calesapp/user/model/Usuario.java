package com.salesianostriana.dam.calesapp.user.model;

import com.salesianostriana.dam.calesapp.model.Contacto;
import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.model.Valoracion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user_entity")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UsuarioRole> roles;

    private boolean enabled = true;

    private String verificationToken;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parada> paradas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacto> contactos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Valoracion> valoraciones = new ArrayList<>();


    public void addParada(Parada parada) {
        paradas.add(parada);
        parada.setUsuario(this);
    }

    public void removeParada(Parada parada) {
        paradas.remove(parada);
        parada.setUsuario(null);
    }

    public void addContacto(Contacto contacto) {
        contactos.add(contacto);
        contacto.setUsuario(this);
    }

    public void removeContacto(Contacto contacto) {
        contactos.remove(contacto);
        contacto.setUsuario(null);
    }

    public void addValoracion(Valoracion valoracion) {
        valoraciones.add(valoracion);
        valoracion.setUsuario(this);
    }

    public void removeValoracion(Valoracion valoracion) {
        valoraciones.remove(valoracion);
        valoracion.setUsuario(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
