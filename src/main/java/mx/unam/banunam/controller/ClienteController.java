package mx.unam.banunam.controller;

import jakarta.validation.Valid;
import mx.unam.banunam.model.Cliente;
import mx.unam.banunam.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping(path = "/")
    public ResponseEntity<List<Cliente>> getAll(){
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping(path = "/{noCliente}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer noCliente){
        Cliente cliente = clienteService.buscarClientePorNoCliente(noCliente);
        return cliente == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(cliente);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) throws URISyntaxException{
        if(cliente.getNoCliente() != null)
            return ResponseEntity.badRequest().build();
        Cliente clienteNuevo = clienteService.salvaCliente(cliente);
        URI location = new URI("/api/clientes/" + clienteNuevo.getNoCliente());
        return ResponseEntity.created(location).body(clienteNuevo);
    }

    @PutMapping(path = "/{noCliente}")
    public ResponseEntity<Cliente> saveCliente(@PathVariable Integer noCliente, @RequestBody Cliente cliente) throws URISyntaxException{
        cliente.setNoCliente(noCliente);
        URI location = new URI("/api/clientes" + cliente.getNoCliente());
        Cliente clienteBD = clienteService.buscarClientePorNoCliente(noCliente);
        return clienteBD != null ?
                ResponseEntity.ok(clienteService.salvaCliente(cliente)) :
                ResponseEntity.created(location).body(clienteService.salvaCliente(cliente));
    }
}
