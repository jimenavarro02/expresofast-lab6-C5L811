package com.expresofast.dto;

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