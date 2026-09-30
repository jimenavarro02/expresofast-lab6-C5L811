package com.expresofast.controller;

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