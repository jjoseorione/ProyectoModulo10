package mx.unam.banunam.repository;

import mx.unam.banunam.model.MovimientoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoPrestamoRepository extends JpaRepository<MovimientoPrestamo, Long> {
}
