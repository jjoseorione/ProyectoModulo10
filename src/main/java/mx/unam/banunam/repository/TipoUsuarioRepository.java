package mx.unam.banunam.repository;

import mx.unam.banunam.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario,Integer> {
    Optional<TipoUsuario> findByAlias(String alias);
}
