package cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto;

import java.math.BigDecimal;

public record VehiculoResponseDTO(
        Integer id,
        String placa,
        BigDecimal capacidadKg,
        String estado,
        Integer empresaId) {
}
