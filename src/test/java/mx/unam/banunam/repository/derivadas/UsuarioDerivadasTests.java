package mx.unam.banunam.repository.derivadas;

import mx.unam.banunam.model.Usuario;
import mx.unam.banunam.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class UsuarioDerivadasTests {
    @Autowired
    UsuarioRepository usuarioRepository;

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

    @DisplayName(value = "Buscar Usuario por usuario")
    @Test
    @Transactional
    void findUsuarioByUsuario(){
        final String nombreUsuario = "XMX7265";
        System.out.println("Buscar Usuario por usuario");
        Usuario usuario = usuarioRepository.findByUsuario(nombreUsuario).orElse(null);
        Assertions.assertNotNull(usuario);
        System.out.println("El usuario encontrado es " + usuario);
    }

    @DisplayName(value = "Buscar Usuarios por alias de tipoUsuario")
    @Test
    @Transactional
    void findUsuariosByAliasTipoUsuario(){
        final String alias1 = "ADMIN";
        final String alias2 = "EXEC";
        System.out.println("Buscar Usuario por alias: " + alias1);
        usuarioRepository.findByTipoUsuarioAlias(alias1).forEach(System.out::println);
        System.out.println("Buscar Usuario por alias: " + alias2);
        usuarioRepository.findByTipoUsuarioAlias(alias2).forEach(System.out::println);


    }
}
