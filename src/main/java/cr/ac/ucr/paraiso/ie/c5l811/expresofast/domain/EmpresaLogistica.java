package cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "EmpresaLogistica")
public class EmpresaLogistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Integer id;
    @Column(name = "nombre", length = 100)
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
