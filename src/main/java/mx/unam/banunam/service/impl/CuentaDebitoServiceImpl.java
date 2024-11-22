package mx.unam.banunam.service.impl;

import mx.unam.banunam.model.CuentaDebito;
import mx.unam.banunam.model.MovimientoDebito;
import mx.unam.banunam.repository.CuentaDebitoRepository;
import mx.unam.banunam.repository.MovimientoDebitoRepository;
import mx.unam.banunam.service.CuentaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaDebitoServiceImpl implements CuentaDebitoService {
    @Autowired
    CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    MovimientoDebitoRepository movimientoDebitoRepository;

    @Override
    public CuentaDebito buscarCuentaDebitoPorNoCuenta(Integer id) {
        return cuentaDebitoRepository.findById(id).orElse(null);
    }

    @Override
    public List<MovimientoDebito> buscarMovimientosPorNoCuenta(Integer id) {
        return movimientoDebitoRepository.findByCuentaDebitoNoCuenta(id);
    }

    @Override
    public CuentaDebito crearCuentaDebito(CuentaDebito cuentaDebito) {
        return cuentaDebitoRepository.save(cuentaDebito);
    }
}
