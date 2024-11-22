package mx.unam.banunam.service.impl;

import mx.unam.banunam.dto.UsuarioDto;
import mx.unam.banunam.errorhandling.exception.TipoUsuarioNotExistsException;
import mx.unam.banunam.model.TipoUsuario;
import mx.unam.banunam.model.Usuario;
import mx.unam.banunam.repository.TipoUsuarioRepository;
import mx.unam.banunam.repository.UsuarioRepository;
import mx.unam.banunam.service.UsuarioDtoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioDtoServiceImpl implements UsuarioDtoService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDto convertToDto(Usuario usuario){
        UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);
        if(usuario.getTipoUsuario() != null)
            usuarioDto.setTipoUsuario(usuario.getTipoUsuario().getAlias());
        return usuarioDto;
    }

    @Override
    public Usuario convertToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        if(usuarioDto.getTipoUsuario() != null && !usuarioDto.getTipoUsuario().isEmpty()){
            TipoUsuario tipoUsuario = tipoUsuarioRepository.findByAlias(usuarioDto.getTipoUsuario()).orElse(null);
            if(tipoUsuario == null)
                throw new TipoUsuarioNotExistsException(usuarioDto.getTipoUsuario());
            usuario.setTipoUsuario(tipoUsuario);
        }
        return usuario;
    }

    @Override
    public UsuarioDto buscarUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuario == null ? null : convertToDto(usuario);
    }

    @Override
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> listarUsuariosPorTipoUsuarioAlias(String alias) {
        return usuarioRepository.findByTipoUsuarioAlias(alias).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.save(convertToEntity(usuarioDto));
        return convertToDto(usuario);
    }

    @Override
    public Boolean eliminarUsuario(Integer id) {
        Boolean respuesta = usuarioRepository.findById(id).isPresent();
        usuarioRepository.deleteById(id);
        return respuesta;
    }
}
