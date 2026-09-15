package cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record EnvioRequestDTO(
        @NotBlank(message = "El código de rastreo es obligatorio") @Pattern(regexp = "^EXP-\\d{4}$", message = "Formato inválido. Ejemplo: EXP-1234") String codigoRastreo,
        @NotBlank(message = "La dirección destino es obligatoria") String direccionDestino,
        @NotNull @Positive(message = "El peso debe ser mayor a cero") BigDecimal pesoKg,
        @NotNull @Positive(message = "El costo debe ser mayor a cero") BigDecimal costo,
        @NotNull @Positive(message = "El vehículo es obligatorio") Integer vehiculoId,
        @NotNull @Positive(message = "El conductor es obligatorio") Integer conductorId) {
}
