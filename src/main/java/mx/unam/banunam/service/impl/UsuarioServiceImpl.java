package mx.unam.banunam.service.impl;

import mx.unam.banunam.model.Usuario;
import mx.unam.banunam.repository.UsuarioRepository;
import mx.unam.banunam.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) (usuarioRepository.findAll());
    }

    @Override
    public List<Usuario> listarUsuariosPorTipoUsuarioAlias(String alias) {
        return usuarioRepository.findByTipoUsuarioAlias(alias);
    }

    //ToDo: Agregar validación de fechaExpUsuario y fechaExpContrasena. Probarlo en los tests.
    @Override
    public Boolean loginUsuarioCustomerCare(String usuario, String contrasena) {
        Usuario usuarioBD = usuarioRepository.findByUsuario(usuario).orElse(null);
        boolean acceso = usuarioBD != null && usuarioBD.getEstatus().equals('A') && usuarioBD.getTipoUsuario().getTipoUsuario() == 2;
        //Credenciales correctas
        if(acceso && usuarioBD.getContrasena().equals(contrasena)) {
            usuarioBD.setIntentos(0);
            usuarioRepository.save(usuarioBD);
            return Boolean.TRUE;
        }
        //Contraseña incorrecta: se suma un intento y se bloquea el usuario si se llegó a 3 intentos
        if(acceso){
            usuarioBD.setIntentos(usuarioBD.getIntentos() + 1);
            if(usuarioBD.getIntentos() == 3)
                usuarioBD.setEstatus('B');
            usuarioRepository.save(usuarioBD);
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

}
