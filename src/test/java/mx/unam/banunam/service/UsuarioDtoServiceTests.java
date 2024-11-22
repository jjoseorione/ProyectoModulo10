package mx.unam.banunam.service;

import mx.unam.banunam.dto.UsuarioDto;
import mx.unam.banunam.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class UsuarioDtoServiceTests {
    @Autowired
    UsuarioDtoService usuarioDtoService;
    @Autowired
    UsuarioService usuarioService;

    private final Integer ID = 1;

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

    @DisplayName("Prueba conversión de Usuario a UsuarioDto")
    @Test
    void convertirUsuarioAUsuarioDto(){
        Usuario usuario = usuarioService.buscarUsuarioPorId(ID);
        UsuarioDto usuarioDto = usuarioDtoService.buscarUsuarioPorId(ID);
        Assertions.assertEquals(usuario.getTipoUsuario().getAlias(), usuarioDto.getTipoUsuario());
        System.out.println(usuario);
        System.out.println(usuarioDto);
    }
}
