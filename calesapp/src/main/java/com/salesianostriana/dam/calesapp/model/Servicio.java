package com.salesianostriana.dam.calesapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cochero_id", foreignKey = @ForeignKey(name = "fk_servicio_cochero"))
    private Cochero cochero;
    @OneToMany(mappedBy = "servicio", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Contacto> contactos = new HashSet<>();
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Valoracion> valoraciones = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Servicio servicio = (Servicio) o;
        return Objects.equals(id, servicio.id) && tipoServicio == servicio.tipoServicio
                && Objects.equals(tarifa, servicio.tarifa) && Objects.equals(duracion, servicio.duracion)
                && Objects.equals(descripcion, servicio.descripcion)
                && Objects.equals(disponibilidad, servicio.disponibilidad) && Objects.equals(cochero, servicio.cochero)
                && Objects.equals(contactos, servicio.contactos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoServicio, tarifa, duracion, descripcion, disponibilidad, cochero, contactos);
    }
}