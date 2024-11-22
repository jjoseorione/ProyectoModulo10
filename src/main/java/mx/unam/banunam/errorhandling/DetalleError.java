package mx.unam.banunam.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleError {
    private String statusCode;
    private String mensaje;
    private String detalle;
    private LocalDateTime timeStamp;
}
