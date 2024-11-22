package mx.unam.banunam.repository;

import mx.unam.banunam.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {
}
