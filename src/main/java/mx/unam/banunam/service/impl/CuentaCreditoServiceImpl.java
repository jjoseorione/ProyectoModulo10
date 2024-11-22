package mx.unam.banunam.service.impl;

import mx.unam.banunam.model.CuentaCredito;
import mx.unam.banunam.model.CuentaDebito;
import mx.unam.banunam.model.MovimientoCredito;
import mx.unam.banunam.repository.CuentaCreditoRepository;
import mx.unam.banunam.repository.CuentaDebitoRepository;
import mx.unam.banunam.repository.MovimientoCreditoRepository;
import mx.unam.banunam.service.CuentaCreditoService;
import mx.unam.banunam.service.CuentaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaCreditoServiceImpl implements CuentaCreditoService {
    @Autowired
    CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    MovimientoCreditoRepository movimientoCreditoRepository;

    @Override
    public CuentaCredito buscarCuentaCreditoPorNoCuenta(Integer id) {
        return cuentaCreditoRepository.findById(id).orElse(null);
    }

    @Override
    public List<MovimientoCredito> buscarMovimientosPorNoCuenta(Integer id) {
        return movimientoCreditoRepository.findByCuentaCreditoNoCuenta(id);
    }
}
