package mx.unam.banunam.service;

import mx.unam.banunam.model.CuentaCredito;
import mx.unam.banunam.model.CuentaDebito;
import mx.unam.banunam.model.MovimientoCredito;
import mx.unam.banunam.model.MovimientoDebito;

import java.util.List;

public interface CuentaCreditoService {
    CuentaCredito buscarCuentaCreditoPorNoCuenta(Integer id);
    List<MovimientoCredito> buscarMovimientosPorNoCuenta(Integer id);
}