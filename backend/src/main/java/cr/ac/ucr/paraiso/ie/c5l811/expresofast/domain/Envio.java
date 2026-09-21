package cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Envio")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envio_id")
    private Integer id;
    @Column(name = "codigo_rastreo", nullable = false, unique = true, length = 20)
    private String codigoRastreo;
    @Column(name = "direccion_destino", nullable = false, length = 250)
    private String direccionDestino;
    @Column(name = "peso_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal pesoKg;
    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;
    @Column(name = "estado_envio", nullable = false, length = 20)
    private String estadoEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoRastreo() {
        return codigoRastreo;
    }

    public void setCodigoRastreo(String v) {
        this.codigoRastreo = v;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String v) {
        this.direccionDestino = v;
    }

    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(BigDecimal v) {
        this.pesoKg = v;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal v) {
        this.costo = v;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String v) {
        this.estadoEnvio = v;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo v) {
        this.vehiculo = v;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor v) {
        this.conductor = v;
    }
}
