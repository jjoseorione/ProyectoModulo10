package mx.unam.banunam.controller;

import jakarta.validation.Valid;
import mx.unam.banunam.dto.UsuarioDto;
import mx.unam.banunam.service.UsuarioDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {
    @Autowired
    UsuarioDtoService usuarioDtoService;

    @GetMapping(path = "/")
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios(@RequestParam(required = false) String aliasTipoUsuario){
        if (aliasTipoUsuario != null && !aliasTipoUsuario.isEmpty())
            return ResponseEntity.ok(usuarioDtoService.listarUsuariosPorTipoUsuarioAlias(aliasTipoUsuario));
        return ResponseEntity.ok(usuarioDtoService.listarUsuarios());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Integer id){
        UsuarioDto usuarioDto = usuarioDtoService.buscarUsuarioPorId(id);
        return usuarioDto == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(usuarioDto);
    }

    @PostMapping(path = "/")
    public ResponseEntity<UsuarioDto> createUsuario(@Valid @RequestBody UsuarioDto usuario) throws URISyntaxException {
        if(usuario.getIdUsuario() != null)
            return ResponseEntity.badRequest().build();
        UsuarioDto usuarioCreado = usuarioDtoService.salvarUsuario(usuario);
        URI location = new URI("/api/usuarios/" + usuarioCreado.getIdUsuario());
        return ResponseEntity.created(location).body(usuarioCreado);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UsuarioDto> saveUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDto usuario
    ) throws URISyntaxException {
        usuario.setIdUsuario(id);
        UsuarioDto usuarioBD = usuarioDtoService.buscarUsuarioPorId(id);
        if(usuarioBD == null)
            return createUsuario(usuario);
        else{
            URI location = new URI("/api/alumnos/" + id);
            return ResponseEntity.ok(usuarioDtoService.salvarUsuario(usuario));
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UsuarioDto> deleteUsuario(@PathVariable Integer id){
        return usuarioDtoService.eliminarUsuario(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();

    }

}
