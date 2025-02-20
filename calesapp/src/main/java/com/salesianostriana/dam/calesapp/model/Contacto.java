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
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;
    private LocalDate fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_contacto_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "servicio_id", foreignKey = @ForeignKey(name = "fk_contacto_servicio"))
    private Servicio servicio;
}

