package mx.unam.banunam.service;

import mx.unam.banunam.model.Usuario;

import java.util.List;

public interface UsuarioService {
    //CRUD básico
    Usuario buscarUsuarioPorId(Integer id);
    List<Usuario> listarUsuarios();

    //CRUD con parámetros
    List<Usuario> listarUsuariosPorTipoUsuarioAlias(String alias);
    //OTROS
    Boolean loginUsuarioCustomerCare(String usuario, String contrasena);
}
