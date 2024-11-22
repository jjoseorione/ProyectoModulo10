package mx.unam.banunam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noCliente;
    @NotBlank(message = "El nombre no puede quedar vacío")
    @Length(min = 1,max = 50, message = "El nombre no puede ser mayor a 50 caracteres")
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El nombre sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String nombre;
    @NotBlank(message = "El apellido no puede quedar vacío")
    @Length(min = 1,max = 50, message = "El apellido no puede ser mayor a 50 caracteres")
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String apPat;
    @Pattern(regexp = "^([A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*)?$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @Length(max = 50, message = "El apellido no puede ser mayor a 50 caracteres")
    private String apMat;
    @NotBlank(message = "El rfc no puede quedar vacío")
    @Pattern(regexp = "^[A-Z]{4}\\d{6}([0-9A-Z]{3})$", message = "El formato de RFC es incorrecto")
    @Length(min=13, max=13, message = "El RFC debe ser de 13 posiciones")
    private String rfc;
    @NotNull(message = "Debe introducir una fecha de nacimiento")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNac;
    @NotNull(message = "Debe introducir el email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El formato de email es incorrecto")
    private String email;
    @Length(min=8, max=30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String contrasena;
    @NotNull(message = "Debe introducir un número telefónico")
    @Length(min=5, max=30, message="El número telefónico debe contener entre 5 y 30 posiciones")
    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "El formato de teléfono es incorrecto")
    private String telefono;
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private CuentaDebito cuentaDebito;
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private CuentaCredito cuentaCredito;
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private CuentaPrestamo cuentaPrestamo;

    @PrePersist
    public void prePersist(){
        if (apMat == null)
            apMat = "";
    }

    public Cliente(Integer noCliente) {
        this.noCliente = noCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(noCliente, cliente.noCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noCliente);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "noCliente=" + noCliente +
                ", nombre='" + nombre + '\'' +
                ", apPat='" + apPat + '\'' +
                ", apMat='" + apMat + '\'' +
                ", rfc='" + rfc + '\'' +
                ", fechaNac=" + fechaNac +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
