package mx.unam.banunam.service;

import mx.unam.banunam.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente buscarClientePorNoCliente(Integer noCliente);
    Cliente salvaCliente(Cliente cliente);
    List<Cliente> listarClientes();
}
