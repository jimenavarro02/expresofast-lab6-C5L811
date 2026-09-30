package com.expresofast.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expresofast.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    
    // Cambia 'CodigoRastreo' por el nombre real del atributo en tu entidad (ej. NumeroRastreo)
    Optional<Envio> findByNumeroRastreo(String numeroRastreo);
    
}