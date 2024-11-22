package mx.unam.banunam.service;

import mx.unam.banunam.model.Usuario;
import mx.unam.banunam.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
class UsuarioServiceTest {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void pruebaLogin(){
        final String usuario = "ZPT0009";
        final String contrasena = "t3mp0r4l";
        Boolean login;

        //acceso correcto
        login = usuarioService.loginUsuarioCustomerCare(usuario, contrasena);
        Usuario usuarioBD = usuarioRepository.findByUsuario(usuario).get();
        assertTrue(login);
        assertEquals(2, usuarioBD.getTipoUsuario().getTipoUsuario());
        assertEquals('A', usuarioBD.getEstatus());
        assertEquals(0, usuarioBD.getIntentos());
        System.out.println("Prueba de acceso correcto exitosa");

        //Intento fallido 1
        login = usuarioService.loginUsuarioCustomerCare(usuario, "incorrecta");
        usuarioBD = usuarioRepository.findByUsuario(usuario).get();
        assertFalse(login);
        assertEquals(2, usuarioBD.getTipoUsuario().getTipoUsuario());
        assertEquals('A', usuarioBD.getEstatus());
        assertEquals(1, usuarioBD.getIntentos());
        System.out.println("Prueba de acceso incorrecto 1 exitosa");

        //Intento fallido 2
        login = usuarioService.loginUsuarioCustomerCare(usuario, "incorrecta");
        usuarioBD = usuarioRepository.findByUsuario(usuario).get();
        assertFalse(login);
        assertEquals(2, usuarioBD.getTipoUsuario().getTipoUsuario());
        assertEquals('A', usuarioBD.getEstatus());
        assertEquals(2, usuarioBD.getIntentos());
        System.out.println("Prueba de acceso incorrecto 2 exitosa");

        //Intento fallido 3: Bloqueo
        login = usuarioService.loginUsuarioCustomerCare(usuario, "incorrecta");
        usuarioBD = usuarioRepository.findByUsuario(usuario).get();
        assertFalse(login);
        assertEquals(2, usuarioBD.getTipoUsuario().getTipoUsuario());
        assertEquals('B', usuarioBD.getEstatus());
        assertEquals(3, usuarioBD.getIntentos());
        System.out.println("Prueba de acceso incorrecto 3 (Bloqueo) exitosa");

        //Usuario Bloqueado. No hay acceso aunque las credenciales sean correctas
        login = usuarioService.loginUsuarioCustomerCare(usuario, contrasena);
        usuarioBD = usuarioRepository.findByUsuario(usuario).get();
        assertFalse(login);
        assertEquals(contrasena, usuarioBD.getContrasena());
        assertEquals(2, usuarioBD.getTipoUsuario().getTipoUsuario());
        assertEquals('B', usuarioBD.getEstatus());
        assertEquals(3, usuarioBD.getIntentos());
        System.out.println("Prueba de acceso incorrecto por bloqueo exitosa");
    }

}