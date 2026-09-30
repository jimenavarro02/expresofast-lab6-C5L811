package com.expresofast.service;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

import com.expresofast.model.Envio;

public interface EnvioService {
    List<Envio> listarEnvios();
    Optional<Envio> obtenerEnvioPorId(Long id);
    Envio guardarEnvio(Envio envio);
    Envio actualizarEstado(Long id, String nuevoEstado);
}
=======
import com.expresofast.dto.*;
import java.util.List;

public interface EnvioService {
    List<EnvioDTO> obtenerTodos();
    EnvioDTO buscarPorRastreo(String codigo);
    EnvioDTO crear(CrearEnvioDTO payload);
    EnvioDTO actualizarEstado(Long id, String nuevoEstado);
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
