package com.expresofast.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaqueteDTO {

    @NotBlank(message = "La descripción del paquete es obligatoria")
    private String descripcion;

    @NotNull(message = "El peso es obligatorio")
    @Min(value = 0, message = "El peso debe ser mayor a 0")
    private Double peso;

    // Getters y Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
}