package com.expresofast.dto;

<<<<<<< HEAD
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class CrearEnvioDTO {

    @NotBlank(message = "El número de rastreo es obligatorio")
    private String numeroRastreo;

    @NotEmpty(message = "Debe incluir al menos un paquete")
    @Valid
    private List<PaqueteDTO> paquetes;

    // Getters y Setters
    public String getNumeroRastreo() { return numeroRastreo; }
    public void setNumeroRastreo(String numeroRastreo) { this.numeroRastreo = numeroRastreo; }

    public List<PaqueteDTO> getPaquetes() { return paquetes; }
    public void setPaquetes(List<PaqueteDTO> paquetes) { this.paquetes = paquetes; }
}
=======
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearEnvioDTO(
        @NotBlank(message = "El destinatario es obligatorio")
        String destinatario,

        @NotBlank(message = "La dirección es obligatoria")
        String direccionDestino,

        @NotNull(message = "El monto del flete es obligatorio")
        @Positive(message = "El monto del flete debe ser mayor que 0")
        Double montoFlete
) {}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
