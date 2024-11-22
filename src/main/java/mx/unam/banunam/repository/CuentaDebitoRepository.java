package mx.unam.banunam.repository;

import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.model.CuentaDebito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaDebitoRepository extends JpaRepository<CuentaDebito,Integer> {
    Optional<CuentaDebito> findByClienteNoCliente(Integer noCliente);
}
