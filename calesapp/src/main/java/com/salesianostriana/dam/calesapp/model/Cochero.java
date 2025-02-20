package com.salesianostriana.dam.calesapp.model;

import com.salesianostriana.dam.calesapp.model.Servicio;
import com.salesianostriana.dam.calesapp.user.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("COCHERO")
public class Cochero extends Usuario {

    private String detallesContacto;
    private String ubicacion;

    @OneToMany(mappedBy = "cochero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios = new ArrayList<>();
}

