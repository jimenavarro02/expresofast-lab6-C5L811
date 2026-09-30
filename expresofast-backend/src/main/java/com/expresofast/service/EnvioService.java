package com.expresofast.service;

import java.util.List;
import java.util.Optional;

import com.expresofast.model.Envio;

public interface EnvioService {
    List<Envio> listarEnvios();
    Optional<Envio> obtenerEnvioPorId(Long id);
    Envio guardarEnvio(Envio envio);
    Envio actualizarEstado(Long id, String nuevoEstado);
}