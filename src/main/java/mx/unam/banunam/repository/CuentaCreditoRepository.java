package mx.unam.banunam.repository;

import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.model.CuentaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaCreditoRepository extends JpaRepository<CuentaCredito, Integer> {
    Optional<CuentaCredito> findByClienteNoCliente(Integer noCliente);
    //List<CuentaCredito> findByLimCreditoGreaterThan(Double limCredito);
}
