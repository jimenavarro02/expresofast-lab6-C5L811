package cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehiculo_id")
    private Integer id;
    @Column(name = "placa", nullable = false, unique = true, length = 20)
    private String placa;
    @Column(name = "capacidad_kg", nullable = false)
    private java.math.BigDecimal capacidadKg;
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaLogistica empresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public java.math.BigDecimal getCapacidadKg() {
        return capacidadKg;
    }

    public void setCapacidadKg(java.math.BigDecimal capacidadKg) {
        this.capacidadKg = capacidadKg;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EmpresaLogistica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaLogistica empresa) {
        this.empresa = empresa;
    }
}
