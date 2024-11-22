package mx.unam.banunam.repository;

import mx.unam.banunam.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String Usuario);
    List<Usuario> findByTipoUsuarioAlias(String alias);

}
