package mx.unam.banunam.repository;

import mx.unam.banunam.model.MovimientoDebito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoDebitoRepository extends JpaRepository<MovimientoDebito, Long> {
    List<MovimientoDebito> findByCuentaDebitoNoCuenta(Integer noCuenta);
}
