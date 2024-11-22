package mx.unam.banunam.service.impl;

import mx.unam.banunam.dto.CuentaCreditoDto;
import mx.unam.banunam.errorhandling.exception.ClienteAlreadyHasCreditAccountException;
import mx.unam.banunam.errorhandling.exception.ClienteNotExistsException;
import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.model.CuentaCredito;
import mx.unam.banunam.repository.ClienteRepository;
import mx.unam.banunam.repository.CuentaCreditoRepository;
import mx.unam.banunam.service.CuentaCreditoDtoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaCreditoDtoServiceImpl implements CuentaCreditoDtoService {
    @Autowired
    private CuentaCreditoRepository cuentaCreditoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CuentaCreditoDto convertToDto(CuentaCredito cuentaCredito) {
        return modelMapper.map(cuentaCredito, CuentaCreditoDto.class);
    }

    @Override
    public CuentaCredito convertToEntity(CuentaCreditoDto cuentaCreditoDto) {
        CuentaCredito cuentaCredito = modelMapper.map(cuentaCreditoDto, CuentaCredito.class);
        if(cuentaCreditoDto.getNoCliente() != null){
            Cliente cliente = clienteRepository.findById(cuentaCreditoDto.getNoCliente()).orElse(null);
            if(cliente == null)
                throw new ClienteNotExistsException(cuentaCreditoDto.getNoCliente());
            cuentaCredito.setCliente(cliente);
        }
        return cuentaCredito;
    }

    @Override
    public CuentaCreditoDto buscarCuentaCreditoPorNoCuenta(Integer noCuenta) {
        CuentaCredito cuentaCredito = cuentaCreditoRepository.findById(noCuenta).orElse(null);
        return cuentaCredito == null ? null : convertToDto(cuentaCredito);
    }

    @Override
    public List<CuentaCreditoDto> listarCuentasCredito() {
        return cuentaCreditoRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CuentaCreditoDto salvarCuentaCredito(CuentaCreditoDto cuentaCreditoDto) {
        //Se revisa si el cliente ya tiene una cuenta de crédito
        if(cuentaCreditoRepository.findByClienteNoCliente(cuentaCreditoDto.getNoCliente()).isPresent())
            throw new ClienteAlreadyHasCreditAccountException(cuentaCreditoDto.getNoCliente());
        CuentaCredito cuentaCredito = cuentaCreditoRepository.save(convertToEntity(cuentaCreditoDto));
        return convertToDto(cuentaCredito);
    }
}
