package com.expresofast.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paquetes")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }
}