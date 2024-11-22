package mx.unam.banunam.service;

import mx.unam.banunam.dto.CuentaCreditoDto;
import mx.unam.banunam.model.CuentaCredito;

import java.util.List;

public interface CuentaCreditoDtoService {
    //Mappers
    CuentaCreditoDto convertToDto(CuentaCredito cuentaCredito);
    CuentaCredito convertToEntity(CuentaCreditoDto cuentaCreditoDto);

    //CRUD básico
    CuentaCreditoDto buscarCuentaCreditoPorNoCuenta(Integer noCuenta);
    List<CuentaCreditoDto> listarCuentasCredito();
    CuentaCreditoDto salvarCuentaCredito(CuentaCreditoDto cuentaCreditoDto);
}
