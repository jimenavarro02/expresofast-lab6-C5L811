package com.expresofast.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Asegúrate de que el nombre de la propiedad sea exactamente el mismo que usa tu repositorio
    private String numeroRastreo; 
    
    private String estado;

    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paquete> paquetes;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroRastreo() { return numeroRastreo; }
    public void setNumeroRastreo(String numeroRastreo) { this.numeroRastreo = numeroRastreo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Paquete> getPaquetes() { return paquetes; }
    public void setPaquetes(List<Paquete> paquetes) { this.paquetes = paquetes; }
}