package mx.unam.banunam.repository;

import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.model.CuentaPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaPrestamoRepository extends JpaRepository<CuentaPrestamo, Integer> {
    Optional<CuentaPrestamo> findByCliente(Cliente cliente);
}
