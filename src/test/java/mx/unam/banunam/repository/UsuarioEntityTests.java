package mx.unam.banunam.repository;

import mx.unam.banunam.model.TipoMovimiento;
import mx.unam.banunam.model.TipoUsuario;
import mx.unam.banunam.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class UsuarioEntityTests {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    private final Integer ID_USUARIO = 1;

    @BeforeEach
    void espaciado(){
        System.out.println();
        System.out.println("••••••••••••••••••••••••••••••");
    }
    @AfterEach
    void espaciadof(){
        System.out.println("••••••••••••••••••••••••••••••");
        System.out.println();
    }

    @DisplayName(value = "Buscar Usuario por id")
    @Test
    @Transactional
    void findUsuarioById(){
        System.out.println("Buscar usuario por id");
        Usuario usuario = usuarioRepository.findById(ID_USUARIO).orElse(null);
        Assertions.assertNotNull(usuario);
        System.out.println("El usuario es: " + usuario);
    }

    @DisplayName(value = "Buscar todos los usuarios")
    @Test
    @Transactional
    void findAllUsuarios(){
        System.out.println("Listar todos los usuarios");
        usuarioRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Crear usuario")
    @Test
    @Transactional
    void createUsuario(){
        System.out.println("Crear usuario");
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(1).orElse(null);
        if(tipoUsuario != null) {
            Usuario usuario = Usuario.builder()
                    .idUsuario(null)
                    .usuario("PRUEB01")
                    .nombre("Juan")
                    .apPat("Sánchez")
                    .apMat("Escobar")
                    .email("juan.sanchez@gmail.com")
                    .contrasena("t3mp0r4l")
                    .intentos(null)
                    .estatus('A')
                    .tipoUsuario(tipoUsuario)
                    .fechaExpUsuario(null)
                    .fechaExpContrasena(null)
                    .build();
            usuarioRepository.save(usuario);
            System.out.println("Usuario creado: \n" + usuario);
        }
    }

    @DisplayName(value = "Eliminar usuario por Id")
    @Test
    @Transactional
    void deleteUsuarioById(){
        System.out.println("Eliminar usuario por id");
        usuarioRepository.deleteById(1);
        System.out.println("El usuario fue eliminado");
    }
}
