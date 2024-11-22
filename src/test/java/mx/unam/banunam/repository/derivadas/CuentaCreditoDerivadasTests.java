package mx.unam.banunam.repository.derivadas;

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

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class CuentaCreditoDerivadasTests {
    @Autowired
    CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 2;
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
    @DisplayName(value = "Buscar cuenta crédito por cliente")
    @Test
    void findCuentaDebitoByCliente(){
        System.out.println("Buscar cuenta de crédito por cliente " + NO_CLIENTE);
        cuentaCreditoRepository.findByClienteNoCliente(NO_CLIENTE).ifPresent(System.out::println);
    }

}
