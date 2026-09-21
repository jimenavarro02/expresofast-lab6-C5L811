package cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BitacoraEnvio")
public class BitacoraEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bitacora_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;
    @Column(name = "estado_anterior", nullable = false, length = 20)
    private String estadoAnterior;
    @Column(name = "estado_nuevo", nullable = false, length = 20)
    private String estadoNuevo;
    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @Column(name = "observaciones", length = 250)
    private String observaciones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer v) {
        id = v;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio v) {
        envio = v;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String v) {
        estadoAnterior = v;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String v) {
        estadoNuevo = v;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime v) {
        fechaCambio = v;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario v) {
        usuario = v;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String v) {
        observaciones = v;
    }
}
