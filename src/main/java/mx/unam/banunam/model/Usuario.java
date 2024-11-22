package mx.unam.banunam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String usuario;
    private String nombre;
    private String apPat;
    private String apMat;
    private String email;
    private String contrasena;
    private Byte intentos;
    private Character estatus;
    @ManyToOne(targetEntity = TipoUsuario.class)
    @JoinColumn(name = "tipoUsuario")
    private TipoUsuario tipoUsuario;
    private LocalDate fechaExpUsuario;
    private LocalDate fechaExpContrasena;

    @PrePersist
    public void prePersist(){
        if (apMat == null)
            apMat = "";
        if (intentos == null)
            intentos = 0;
        if (fechaExpUsuario == null)
            fechaExpUsuario = LocalDate.now().plusMonths(6);
        if (fechaExpContrasena == null)
            fechaExpContrasena = LocalDate.now().plusMonths(1);
        if (estatus == null)
            estatus = 'B';
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos.byteValue();
    }

    public Integer getIntentos() {
        return intentos.intValue();
    }
}