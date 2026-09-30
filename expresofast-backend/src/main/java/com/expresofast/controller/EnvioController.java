package com.expresofast.controller;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expresofast.model.Envio;
import com.expresofast.service.EnvioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/envios") 
@CrossOrigin(origins = "http://localhost:4200")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping 
    public List<Envio> obtenerEnvios() {
        return envioService.listarEnvios();
    }

    @PostMapping
    public Envio guardarEnvio(@RequestBody Envio envio) {
    return envioService.guardarEnvio(envio);
}
}
=======
import com.expresofast.dto.*;
import com.expresofast.service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/envios")
@CrossOrigin(origins = "http://localhost:4200")
public class EnvioController {

    private final EnvioService service;

    public EnvioController(EnvioService service) {
        this.service = service;
    }

    @GetMapping
    public List<EnvioDTO> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/rastreo/{codigo}")
    public EnvioDTO buscarPorRastreo(@PathVariable String codigo) {
        return service.buscarPorRastreo(codigo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnvioDTO crear(@Valid @RequestBody CrearEnvioDTO payload) {
        return service.crear(payload);
    }

    @PatchMapping("/{id}/estado")
    public EnvioDTO actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoDTO payload) {
        return service.actualizarEstado(id, payload.estado());
    }
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
