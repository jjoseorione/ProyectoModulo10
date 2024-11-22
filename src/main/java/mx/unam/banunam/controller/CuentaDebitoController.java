package mx.unam.banunam.controller;

import jakarta.validation.Valid;
import mx.unam.banunam.dto.CuentaDebitoDto;
import mx.unam.banunam.service.CuentaDebitoDtoService;
import mx.unam.banunam.service.CuentaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cuentas-debito", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuentaDebitoController {
    @Autowired
    CuentaDebitoDtoService cuentaDebitoDtoService;

    @GetMapping(path = "/")
    public ResponseEntity<List<CuentaDebitoDto>> listCuentasDebito(){
        return ResponseEntity.ok(cuentaDebitoDtoService.listarCuentasDebito());
    }

    @GetMapping(path = "/{noCuenta}")
    public ResponseEntity<CuentaDebitoDto> getCuentaDebitoByNoCuenta(@PathVariable Integer noCuenta){
        CuentaDebitoDto cuentaDebitoDto = cuentaDebitoDtoService.buscarCuentaDebitoPorNoCuenta(noCuenta);
        return cuentaDebitoDto == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(cuentaDebitoDto);
    }

    @PostMapping(path = "/")
    public ResponseEntity<CuentaDebitoDto> createCuentaDebito(@Valid @RequestBody CuentaDebitoDto cuentaDebito) throws URISyntaxException {
        if(cuentaDebito.getNoCuenta() != null)
            return ResponseEntity.badRequest().build(); //ToDo crear excepción para este caso
        CuentaDebitoDto cuentaDebitoCreada = cuentaDebitoDtoService.salvarCuentaDebito(cuentaDebito);
        URI location = new URI("/api/cuentas-debito/" + cuentaDebitoCreada.getNoCuenta());
        return ResponseEntity.created(location).body(cuentaDebitoCreada);
    }
}
