package mx.unam.banunam.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CuentaDebitoDto {
    private Integer noCuenta;
    @NotNull(message = "La cuenta debe asociarse a un cliente")
    private Integer noCliente;
    @Digits(integer = 5, fraction = 2)
    @Min(0)
    private BigDecimal saldo;
}
