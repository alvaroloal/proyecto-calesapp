package com.salesianostriana.dam.calesapp.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoServicio tipoServicio;

    private Double tarifa;
    private Integer duracion;
    private String descripcion;
    private Boolean disponibilidad;

    @ManyToOne
    @JoinColumn(name = "cochero_id", foreignKey = @ForeignKey(name = "fk_servicio_cochero"))
    private Cochero cochero;

    @OneToMany(mappedBy = "servicio", orphanRemoval = true)
    private Set<Contacto> contactos = new HashSet<>();
}
