package mx.unam.banunam.service.impl;

import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.repository.ClienteRepository;
import mx.unam.banunam.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente buscarClientePorNoCliente(Integer noCliente) {
        return clienteRepository.findById(noCliente).orElse(null);
    }

    @Override
    public Cliente salvaCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }
}
