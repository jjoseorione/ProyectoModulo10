package mx.unam.banunam.controller;

import jakarta.validation.Valid;
import mx.unam.banunam.dto.CuentaCreditoDto;
import mx.unam.banunam.service.CuentaCreditoDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cuentas-credito", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuentaCreditoController {
    @Autowired
    CuentaCreditoDtoService cuentaCreditoDtoService;

    @GetMapping(path = "/")
    public ResponseEntity<List<CuentaCreditoDto>> listCuentasCredito(){
        return ResponseEntity.ok(cuentaCreditoDtoService.listarCuentasCredito());
    }

    @GetMapping(path = "/{noCuenta}")
    public ResponseEntity<CuentaCreditoDto> getCuentaCreditoByNoCuenta(@Valid @PathVariable Integer noCuenta){
        CuentaCreditoDto cuentaCreditoDto = cuentaCreditoDtoService.buscarCuentaCreditoPorNoCuenta(noCuenta);
        return cuentaCreditoDto == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(cuentaCreditoDto);
    }

    @PostMapping(path = "/")
    public ResponseEntity<CuentaCreditoDto> createCuentaCredito(@Valid @RequestBody CuentaCreditoDto cuentaCredito) throws URISyntaxException {
        if(cuentaCredito.getNoCuenta() != null)
            return ResponseEntity.badRequest().build(); //ToDo crear excepción para este caso
        CuentaCreditoDto cuentaCreditoCreada = cuentaCreditoDtoService.salvarCuentaCredito(cuentaCredito);
        URI location = new URI("/api/cuentas-credito/" + cuentaCreditoCreada.getNoCuenta());
        return ResponseEntity.created(location).body(cuentaCreditoCreada);
    }

}
