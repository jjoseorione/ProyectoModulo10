package mx.unam.banunam.webclient.controller;

import mx.unam.banunam.dto.UsuarioDto;
import mx.unam.banunam.webclient.services.UsuarioFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioFrontController {
    @Autowired
    private UsuarioFrontService usuarioFrontService;

    @GetMapping(path = "/front/usuarios/{id}")
    public String getUsuarioById(@PathVariable Integer id, Model model){
        UsuarioDto usuarioDto = usuarioFrontService.getUsuarioById(id);
        model.addAttribute("usuario", usuarioDto);
        return "usuario_detalle";
    }

    @GetMapping(path = "/front/usuarios/")
    public String getAllUsuarios(Model model){
        model.addAttribute("usuarios", usuarioFrontService.getAllUsuarios());
        return "usuarios";
    }

    @GetMapping(path = "/front/usuarios/{id}/editar")
    public String getFormEditar(@PathVariable Integer id, Model model){
        UsuarioDto usuarioDto = usuarioFrontService.getUsuarioById(id);
        model.addAttribute("usuario", usuarioDto);
        return "editar_usuario";
    }

    @PutMapping(path = "/front/usuarios/{id}")
    public String updateUsuario(@PathVariable Integer id, @RequestBody UsuarioDto usuario, Model model){
        System.out.println("Entró");
        UsuarioDto usuarioActualizado = usuarioFrontService.updateUsuario(id, usuario);
        model.addAttribute("usuario", usuarioActualizado);
        return "editar_usuario";
    }

    @DeleteMapping(path = "/front/usuarios/{id}")
    public String eliminar(@PathVariable Integer id, Model model){
        usuarioFrontService.deleteUsuario(id);
        model.addAttribute("usuarios", usuarioFrontService.getAllUsuarios());
        return "usuarios";
    }

    @GetMapping(path = "/front/usuarios/agregar")
    public String getFormEditar(Model model){
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuario", usuarioDto);
        return "agregar_usuario";
    }

    @PostMapping(path = "/front/usuarios/")
    public String createUsuario(@RequestBody UsuarioDto usuario, Model model){
        UsuarioDto usuarioCreado = usuarioFrontService.createUsuario(usuario);
        model.addAttribute("usuario", usuarioCreado);
        return "agregar_usuario";
    }
}
