package cr.ac.ucr.paraiso.ie.c5l811.expresofast.controller;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Vehiculo;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoRepository repo;

    public VehiculoController(VehiculoRepository r) {
        repo = r;
    }

    @GetMapping
    public List<Vehiculo> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Vehiculo uno(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow();
    }
}
