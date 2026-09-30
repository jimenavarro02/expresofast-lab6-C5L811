package com.expresofast.service;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expresofast.model.Envio;
import com.expresofast.repository.EnvioRepository;
=======
import com.expresofast.dto.*;
import com.expresofast.model.*;
import com.expresofast.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9

@Service
public class EnvioServiceImpl implements EnvioService {

<<<<<<< HEAD
    @Autowired
    private EnvioRepository envioRepository;

    @Override
    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    @Override
    public Optional<Envio> obtenerEnvioPorId(Long id) {
        return envioRepository.findById(id);
    }

    @Override
    public Envio guardarEnvio(Envio envio) {
        return envioRepository.save(envio);
    }
   @Override
    public Envio actualizarEstado(Long id, String nuevoEstado) {
        Optional<Envio> envioOpt = envioRepository.findById(id);
        if (envioOpt.isPresent()) {
            Envio envio = envioOpt.get();
            envio.setEstado(nuevoEstado);
            return envioRepository.save(envio);
        }
        throw new RuntimeException("Envío no encontrado con ID: " + id);
    }
}
=======
    private final EnvioRepository repository;

    public EnvioServiceImpl(EnvioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvioDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EnvioDTO buscarPorRastreo(String codigo) {
        Envio envio = repository.findByCodigoRastreo(codigo)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        return toDTO(envio);
    }

    @Override
    @Transactional
    public EnvioDTO crear(CrearEnvioDTO payload) {
        Envio envio = new Envio();
        envio.setCodigoRastreo(generarCodigoUnico());
        envio.setDestinatario(payload.destinatario());
        envio.setDireccionDestino(payload.direccionDestino());
        envio.setMontoFlete(payload.montoFlete());
        envio.setEstado("PENDIENTE");
        envio.setFechaCreacion(LocalDateTime.now());

        /*
         * El esquema del Laboratorio 9 todavía exige peso, vehículo y conductor.
         * Como el formulario del Laboratorio 10 no los solicita, usamos valores
         * básicos para poder guardar el nuevo registro en la misma base.
         */
        envio.setPesoKg(1.0);

        envio.setVehiculoId(1);
        envio.setConductorId(1);

        return toDTO(repository.save(envio));
    }

    @Override
    @Transactional
    public EnvioDTO actualizarEstado(Long id, String nuevoEstado) {
        Envio envio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));

        String estado = nuevoEstado.trim().toUpperCase();

        if (!List.of("PENDIENTE", "EN_TRANSITO", "ENTREGADO", "CANCELADO").contains(estado)) {
            throw new IllegalArgumentException("Estado no válido");
        }

        envio.setEstado(estado);
        return toDTO(repository.save(envio));
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            int numero = ThreadLocalRandom.current().nextInt(1000, 10000);
            codigo = "EXP-2026-" + numero;
        } while (repository.existsByCodigoRastreo(codigo));
        return codigo;
    }

    private EnvioDTO toDTO(Envio e) {
        return new EnvioDTO(
                e.getId(),
                e.getCodigoRastreo(),
                e.getDestinatario(),
                e.getDireccionDestino(),
                e.getMontoFlete(),
                e.getEstado(),
                e.getFechaCreacion()
        );
    }

}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
