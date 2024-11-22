package mx.unam.banunam.errorhandling;

import jakarta.servlet.http.HttpServletRequest;
import mx.unam.banunam.errorhandling.exception.ClienteAlreadyHasCreditAccountException;
import mx.unam.banunam.errorhandling.exception.ClienteAlreadyHasDebitAccountException;
import mx.unam.banunam.errorhandling.exception.ClienteNotExistsException;
import mx.unam.banunam.errorhandling.exception.TipoUsuarioNotExistsException;
import mx.unam.banunam.model.TipoUsuario;
import mx.unam.banunam.repository.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {
    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @ExceptionHandler(ClienteAlreadyHasCreditAccountException.class)
    public ResponseEntity<DetalleError> clienteYaTieneCuentaDebito(
            ClienteAlreadyHasCreditAccountException ex,
            HttpServletRequest request
    ){
        DetalleError detalle = DetalleError.builder()
                .mensaje(ex.getMessage())
                .detalle("Un cliente sólo puede tener una cuenta de débito")
                .statusCode(HttpStatus.CONFLICT.toString())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }

    @ExceptionHandler(ClienteAlreadyHasDebitAccountException.class)
    public ResponseEntity<DetalleError> clienteYaTieneCuentaCredito(
            ClienteAlreadyHasDebitAccountException ex,
            HttpServletRequest request
    ){
        DetalleError detalle = DetalleError.builder()
                .mensaje(ex.getMessage())
                .detalle("Un cliente sólo puede tener una cuenta de crédito")
                .statusCode(HttpStatus.CONFLICT.toString())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }

    @ExceptionHandler(ClienteNotExistsException.class)
    public ResponseEntity<DetalleError> clienteNoExiste(
            ClienteNotExistsException ex,
            HttpServletRequest request
    ){
        DetalleError detalle = DetalleError.builder()
                .mensaje(ex.getMessage())
                .detalle("No existe el cliente al que intenta asignar la cuenta")
                .statusCode(HttpStatus.CONFLICT.toString())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }

    @ExceptionHandler(TipoUsuarioNotExistsException.class)
    public ResponseEntity<DetalleError> clienteNoExiste(
            TipoUsuarioNotExistsException ex,
            HttpServletRequest request
    ){
        List<String> tiposUsuario = tipoUsuarioRepository.findAll().stream().map(TipoUsuario::getAlias).toList();
        DetalleError detalle = DetalleError.builder()
                .mensaje(ex.getMessage())
                .detalle("Los tipos de usuario válidos son " + tiposUsuario)
                .statusCode(HttpStatus.CONFLICT.toString())
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, ?>> validacionCampos(MethodArgumentNotValidException ex){
        int c = 0;
        HashMap<String, Object> detalles = new HashMap<>();
        detalles.put("mensaje", "Error de validación de campos");
        detalles.put("statusCode", ex.getStatusCode().toString());
        detalles.put("timestamp", LocalDateTime.now().toString());
        HashMap<Integer, String> detalleCampos = new HashMap<>();
        for (FieldError campoError : ex.getBindingResult().getFieldErrors())
            detalleCampos.put(++c , campoError.getField() + ": " + campoError.getDefaultMessage());
        detalles.put("errores", detalleCampos);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalles);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DetalleError> recursoNoExiste(NoResourceFoundException ex, HttpServletRequest request){
        DetalleError detalleError = new DetalleError();
        detalleError.setMensaje("Recurso no existente " + request.getRequestURI());
        detalleError.setStatusCode(HttpStatus.NOT_FOUND.toString());
        detalleError.setDetalle(request.getRequestURI() + " - " + request.getRequestURI());
        detalleError.setTimeStamp(LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalleError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DetalleError> errorConversion(
            MethodArgumentTypeMismatchException ex
    ){
        DetalleError detalle = new DetalleError();
        detalle.setMensaje(ex.getMessage());
        detalle.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        detalle.setTimeStamp(LocalDateTime.now());
        detalle.setDetalle("Propiedad: " + ex.getPropertyName() +
                " Tipo de dato: " + ex.getRequiredType());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }
}
