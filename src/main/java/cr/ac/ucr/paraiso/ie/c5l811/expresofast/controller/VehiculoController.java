package cr.ac.ucr.paraiso.ie.c5l811.expresofast.controller;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.business.VehiculoService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.VehiculoRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.VehiculoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @GetMapping
    public List<VehiculoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public VehiculoResponseDTO uno(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculoResponseDTO crear(@Valid @RequestBody VehiculoRequestDTO dto) {
        return service.crear(dto);
    }
}
