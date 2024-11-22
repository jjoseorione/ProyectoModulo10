package mx.unam.banunam.repository.derivadas;

import mx.unam.banunam.model.TipoUsuario;
import mx.unam.banunam.repository.TipoUsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class TipoUsuarioDerivadasTests {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

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

    @DisplayName(value = "Buscar tipoUsuario por Alias")
    @Test
    void findTipoUsuarioByAlias(){
        final String ALIAS = "ADMIN";
        System.out.println("Buscar tipoUsuario por alias: " + ALIAS);
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByAlias(ALIAS).orElse(null);
        Assertions.assertNotNull(tipoUsuario);
        System.out.println(tipoUsuario);
    }
}
