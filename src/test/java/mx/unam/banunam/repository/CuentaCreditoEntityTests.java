package mx.unam.banunam.repository;

import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.model.CuentaCredito;
import mx.unam.banunam.repository.ClienteRepository;
import mx.unam.banunam.repository.CuentaCreditoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class CuentaCreditoEntityTests{
    @Autowired
    CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 1;
    private final Integer NO_CUENTA = 20000000;

    @BeforeEach
    void espaciado(){
        System.out.println();
        System.out.println("••••••••••••••••••••••••••••••");
    }
    @AfterEach
    void espaciadof(){
        System.out.println("••••••••••••••••••••••••••••••");
        System.out.println();
    }

    @Transactional
    @DisplayName(value = "Buscar todas las cuentas de crédito")
    @Test
    void findAllCuentasCredito(){
        System.out.println("Buscar todas las cuentas de crédito");
        cuentaCreditoRepository.findAll().forEach(System.out::println);
    }

    @DisplayName(value = "Crear cuenta de crédito y asignarla a cliente")
    @Test
    void createCuentaDebitoYAsignarACliente(){
        System.out.println("Crear cuenta de crédito y asignarla a cliente");
        Optional<Cliente> cliente = clienteRepository.findById(NO_CLIENTE);
        if(cliente.isPresent()){
            System.out.println("Cliente: " + cliente);
            CuentaCredito cuentaCredito =
                    CuentaCredito.builder().noCuenta(null).limCredito(BigDecimal.ZERO).cliente(cliente.get()).saldoUtilizado(BigDecimal.ZERO).tasaInteresAnual(BigDecimal.ZERO).build();
            cuentaCreditoRepository.save(cuentaCredito);
            System.out.println("Cuenta de crédito creada: ");
            System.out.println(cuentaCredito);
        }
    }

    @Transactional
    @DisplayName(value = "Buscar cuenta crédito por noCuenta")
    @Test
    void findCuentaCreditoByNoCuenta(){
        System.out.println("Buscar cuenta de crédito por noCuenta");
        cuentaCreditoRepository.findById(NO_CUENTA).ifPresent(System.out::println);
    }


    @Transactional
    @DisplayName(value = "Buscar cliente por noCuenta credito")
    @Test
    void findClienteByCuentaCredito(){
        System.out.println("Buscar cliente por cuenta de credito " + NO_CUENTA);
        Cliente cliente = cuentaCreditoRepository.findById(NO_CUENTA).get().getCliente();
        System.out.println(cliente);
    }
}
