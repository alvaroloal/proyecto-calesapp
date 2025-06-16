package com.salesianostriana.dam.calesapp.model;

import com.salesianostriana.dam.calesapp.user.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "valoracion")
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int puntuacion;
    private String comentario;
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_valoracion_usuario"))
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "servicio_id", foreignKey = @ForeignKey(name = "fk_valoracion_servicio"))
    private Servicio servicio;
}