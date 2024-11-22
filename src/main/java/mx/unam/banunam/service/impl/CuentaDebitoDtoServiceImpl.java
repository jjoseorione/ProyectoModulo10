package mx.unam.banunam.service.impl;

import mx.unam.banunam.dto.CuentaDebitoDto;
import mx.unam.banunam.errorhandling.exception.ClienteAlreadyHasDebitAccountException;
import mx.unam.banunam.errorhandling.exception.ClienteNotExistsException;
import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.model.CuentaDebito;
import mx.unam.banunam.repository.ClienteRepository;
import mx.unam.banunam.repository.CuentaDebitoRepository;
import mx.unam.banunam.service.CuentaDebitoDtoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaDebitoDtoServiceImpl implements CuentaDebitoDtoService {
    @Autowired
    private CuentaDebitoRepository cuentaDebitoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CuentaDebitoDto convertToDto(CuentaDebito cuentaDebito){
        return modelMapper.map(cuentaDebito, CuentaDebitoDto.class);
    }

    @Override
    public CuentaDebito convertToEntity(CuentaDebitoDto cuentaDebitoDto){
        CuentaDebito cuentaDebito = modelMapper.map(cuentaDebitoDto, CuentaDebito.class);
        if(cuentaDebitoDto.getNoCliente() != null){
            Cliente cliente = clienteRepository.findById(cuentaDebitoDto.getNoCliente()).orElse(null);
            if(cliente == null)
                throw new ClienteNotExistsException(cuentaDebitoDto.getNoCliente());
            cuentaDebito.setCliente(cliente);
        }
        return cuentaDebito;
    }

    @Override
    public CuentaDebitoDto buscarCuentaDebitoPorNoCuenta(Integer noCuenta) {
        CuentaDebito cuentaDebito = cuentaDebitoRepository.findById(noCuenta).orElse(null);
        return cuentaDebito == null ? null : convertToDto(cuentaDebito);
    }

    @Override
    public List<CuentaDebitoDto> listarCuentasDebito() {
        return cuentaDebitoRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CuentaDebitoDto salvarCuentaDebito(CuentaDebitoDto cuentaDebitoDto) {
        //Revisar si el cliente ya tiene una cuenta de débito
        if (cuentaDebitoRepository.findByClienteNoCliente(cuentaDebitoDto.getNoCliente()).isPresent())
            throw new ClienteAlreadyHasDebitAccountException(cuentaDebitoDto.getNoCliente());
        CuentaDebito cuentaDebito = cuentaDebitoRepository.save(convertToEntity(cuentaDebitoDto));
        return convertToDto(cuentaDebito);
    }
}
