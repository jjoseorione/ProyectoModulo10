package mx.unam.banunam.service;

import mx.unam.banunam.dto.UsuarioDto;
import mx.unam.banunam.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UsuarioDtoService {
    //Mapper
    UsuarioDto convertToDto(Usuario usuario);
    Usuario convertToEntity(UsuarioDto usuarioDto);

    //CRUD básico
    UsuarioDto buscarUsuarioPorId(Integer id);
    List<UsuarioDto> listarUsuarios();
    UsuarioDto salvarUsuario(UsuarioDto usuarioDto);
    Boolean eliminarUsuario(Integer id);

    //CRUD con parámetros
    List<UsuarioDto> listarUsuariosPorTipoUsuarioAlias(String alias);

    //OTROS
    //ToDo login
}
