package mx.unam.banunam.service;

import mx.unam.banunam.model.CuentaDebito;
import mx.unam.banunam.model.MovimientoDebito;

import java.util.List;
import java.util.Optional;

public interface CuentaDebitoService {
    CuentaDebito buscarCuentaDebitoPorNoCuenta(Integer id);
    List<MovimientoDebito> buscarMovimientosPorNoCuenta(Integer id);
    CuentaDebito crearCuentaDebito(CuentaDebito cuentaDebito);
}
