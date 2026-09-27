package cr.ac.ucr.paraiso.ie.c5l811.expresofast.controller;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.business.EnvioService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {
    private final EnvioService service;

    public EnvioController(EnvioService s) {
        service = s;
    }

    @GetMapping
    public List<EnvioResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/optimizados")
    public List<EnvioResponseDTO> optimizados() {
        return service.optimizados();
    }

    @GetMapping("/bitacora/historial")
    public List<BitacoraResponseDTO> historialBitacora() {
        return service.historialBitacora();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnvioResponseDTO crear(@Valid @RequestBody EnvioRequestDTO dto) {
        return service.crear(dto);
    }

    @PatchMapping("/{id}/estado")
    public EnvioResponseDTO estado(@PathVariable Integer id, @Valid @RequestBody CambioEstadoDTO dto) {
        return service.cambiarEstado(id, dto);
    }

    @GetMapping("/{id}/bitacora")
    public List<BitacoraResponseDTO> bitacora(@PathVariable Integer id) {
        return service.bitacora(id);
    }
}
