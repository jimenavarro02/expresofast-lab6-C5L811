package com.expresofast.model;

<<<<<<< HEAD
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
=======
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Envio")
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
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
=======
    @Column(name = "envio_id")
    private Long id;

    @Column(name = "codigo_rastreo", nullable = false, unique = true)
    private String codigoRastreo;

    @Column(name = "destinatario")
    private String destinatario;

    @Column(name = "direccion_destino", nullable = false)
    private String direccionDestino;

    @Column(name = "costo", nullable = false)
    private Double montoFlete;

    @Column(name = "estado_envio", nullable = false)
    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @Column(name = "vehiculo_id", nullable = false)
    private Integer vehiculoId;

    @Column(name = "conductor_id", nullable = false)
    private Integer conductorId;

    public Long getId() { return id; }
    public String getCodigoRastreo() { return codigoRastreo; }
    public void setCodigoRastreo(String codigoRastreo) { this.codigoRastreo = codigoRastreo; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getDireccionDestino() { return direccionDestino; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }
    public Double getMontoFlete() { return montoFlete; }
    public void setMontoFlete(Double montoFlete) { this.montoFlete = montoFlete; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public Integer getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Integer vehiculoId) { this.vehiculoId = vehiculoId; }
    public Integer getConductorId() { return conductorId; }
    public void setConductorId(Integer conductorId) { this.conductorId = conductorId; }
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
