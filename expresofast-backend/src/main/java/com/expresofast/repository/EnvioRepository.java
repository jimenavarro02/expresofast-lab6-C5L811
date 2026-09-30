package com.expresofast.repository;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expresofast.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    
    // Cambia 'CodigoRastreo' por el nombre real del atributo en tu entidad (ej. NumeroRastreo)
    Optional<Envio> findByNumeroRastreo(String numeroRastreo);
    
}
=======
import com.expresofast.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByCodigoRastreo(String codigoRastreo);
    List<Envio> findByEstadoIgnoreCase(String estado);
    boolean existsByCodigoRastreo(String codigoRastreo);
}
>>>>>>> 1c90a2d51792e18d6e339edf0ebc941348ec1ce9
