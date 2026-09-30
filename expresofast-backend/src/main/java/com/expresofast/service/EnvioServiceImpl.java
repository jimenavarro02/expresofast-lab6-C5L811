package com.expresofast.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expresofast.model.Envio;
import com.expresofast.repository.EnvioRepository;

@Service
public class EnvioServiceImpl implements EnvioService {

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