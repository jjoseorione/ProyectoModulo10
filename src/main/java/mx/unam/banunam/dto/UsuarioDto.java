package mx.unam.banunam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {
    private Integer idUsuario;
    @NotBlank(message = "El usuario no puede quedar vacío")
    @Pattern(regexp = "^(ZPT|XMX)\\d{4}$", message = "El usuario debe ser un ZPT ó un XMX")
    private String usuario;
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El nombre sólo puede contener caracteres alfabéticos y espacios entre palabras")
    @NotBlank(message = "El nombre no puede quedar vacío")
    private String nombre;
    @NotBlank(message = "El apellido no puede quedar vacío")
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String apPat;
    @Pattern(regexp = "^[A-zÀ-ú]+(\\s?[A-zÀ-ú]+)*$", message = "El apellido sólo puede contener caracteres alfabéticos y espacios entre palabras")
    private String apMat;
    @NotBlank(message = "El correo no puede quedar vacío")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank(message = "La contraseña no puede quedar vacía")
    @Length(min=5, max=13)
    private String contrasena;
    private Integer intentos;
    @Pattern(regexp = "^[AB]$")
    private String estatus;
    @NotBlank(message = "El tipo de usuario no puede quedar vacío")
    private String tipoUsuario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaExpUsuario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaExpContrasena;
}
