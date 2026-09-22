package cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record VehiculoRequestDTO(
        @NotBlank String placa,
        @NotNull @Positive BigDecimal capacidadKg,
        @NotNull @Positive Integer empresaId) {
}
