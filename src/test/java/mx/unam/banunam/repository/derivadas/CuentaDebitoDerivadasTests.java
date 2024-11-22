package mx.unam.banunam.repository.derivadas;

import mx.unam.banunam.repository.ClienteRepository;
import mx.unam.banunam.repository.CuentaDebitoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author  José Emmanuel Espino Moya
 * Clase para realizar pruebas de las consultas derivadas pertenecientes
 * a CuentaDebitoRepository
 */

@SpringBootTest
@Sql({"/sql/schema.sql", "/sql/data.sql"})
public class CuentaDebitoDerivadasTests {
    @Autowired
    private CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private final Integer NO_CLIENTE = 2;
    private final Integer NO_CUENTA = 10000000;

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
    @DisplayName(value = "Buscar cuenta débito por cliente")
    @Test
    void findCuentaDebitoByCliente(){
        System.out.println("Buscar cuenta de débito por cliente " + NO_CLIENTE);
        cuentaDebitoRepository.findByClienteNoCliente(NO_CLIENTE).ifPresent(System.out::println);
    }
}
