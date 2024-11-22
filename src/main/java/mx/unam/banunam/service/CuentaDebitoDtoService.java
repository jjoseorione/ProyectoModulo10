package mx.unam.banunam.service;

import mx.unam.banunam.dto.CuentaDebitoDto;
import mx.unam.banunam.model.CuentaDebito;

import java.util.List;

public interface CuentaDebitoDtoService {
    //Mappers
    CuentaDebitoDto convertToDto(CuentaDebito cuentaDebito);
    CuentaDebito convertToEntity(CuentaDebitoDto cuentaDebitoDto);

    //CRUD básico
    CuentaDebitoDto buscarCuentaDebitoPorNoCuenta(Integer id);
    List<CuentaDebitoDto> listarCuentasDebito();
    CuentaDebitoDto salvarCuentaDebito(CuentaDebitoDto cuentaDebitoDto);

}
