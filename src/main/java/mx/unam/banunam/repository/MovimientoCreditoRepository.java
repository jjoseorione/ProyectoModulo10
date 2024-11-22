package mx.unam.banunam.repository;

import mx.unam.banunam.model.MovimientoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoCreditoRepository extends JpaRepository<MovimientoCredito, Long> {
    List<MovimientoCredito> findByCuentaCreditoNoCuenta(Integer noCuenta);

}
