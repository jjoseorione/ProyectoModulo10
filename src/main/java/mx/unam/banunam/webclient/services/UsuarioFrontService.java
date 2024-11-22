package mx.unam.banunam.webclient.services;

import mx.unam.banunam.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioFrontService {
    @Autowired
    private RestTemplate restTemplate;

    public static final String API_URL = "http://localhost:8080/api/usuarios";

    public UsuarioDto getUsuarioById(Integer id){
        String url = API_URL + "/" + id;
        return restTemplate.getForObject(url, UsuarioDto.class);
    }

    public UsuarioDto[] getAllUsuarios(){
        String url = API_URL + "/";
        UsuarioDto[] usuarios = restTemplate.getForObject(url, UsuarioDto[].class);
        System.out.println("Usuarios: " + usuarios.length);
        return usuarios;
    }

    public UsuarioDto createUsuario(UsuarioDto usuario){
        String url = API_URL + "/";
        UsuarioDto usuarioCreado = restTemplate.postForObject(url, usuario, UsuarioDto.class );
        System.out.println(usuarioCreado);
        return usuarioCreado;
    }

    public UsuarioDto updateUsuario(Integer id, UsuarioDto usuario){
        String url = API_URL + "/" + id;
        restTemplate.put(url, usuario);
        return usuario;
    }

    public void deleteUsuario(Integer id){
        String url = API_URL + "/" + id;
        restTemplate.delete(url);
    }
}
