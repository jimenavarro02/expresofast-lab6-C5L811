package cr.ac.ucr.paraiso.ie.c5l811.expresofast.business;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.*;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.*;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.exception.*;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvioService {
    private final EnvioRepository envios;
    private final VehiculoRepository vehiculos;
    private final ConductorRepository conductores;
    private final UsuarioRepository usuarios;
    private final BitacoraEnvioRepository bitacoras;

    public EnvioService(EnvioRepository e, VehiculoRepository v, ConductorRepository c, UsuarioRepository u,
            BitacoraEnvioRepository b) {
        envios = e;
        vehiculos = v;
        conductores = c;
        usuarios = u;
        bitacoras = b;
    }

    public List<EnvioResponseDTO> optimizados() {
        return envios.findAllOptimized().stream().map(this::toDto).toList();
    }

    public List<EnvioResponseDTO> listar() {
        return optimizados();
    }

    public List<BitacoraResponseDTO> historialBitacora() {
        return bitacoras.findAllHistorial().stream()
                .map(b -> new BitacoraResponseDTO(b.getId(), b.getEstadoAnterior(),
                        b.getEstadoNuevo(), b.getFechaCambio(), b.getUsuario().getUsername(), b.getObservaciones()))
                .toList();
    }

    @Transactional
    public EnvioResponseDTO crear(EnvioRequestDTO d) {
        Envio e = new Envio();
        e.setCodigoRastreo(d.codigoRastreo());
        e.setDireccionDestino(d.direccionDestino());
        e.setPesoKg(d.pesoKg());
        e.setCosto(d.costo());
        e.setEstadoEnvio("PENDIENTE");
        e.setVehiculo(vehiculos.findById(d.vehiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado")));
        e.setConductor(conductores.findById(d.conductorId())
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado")));
        return toDto(envios.save(e));
    }

    @Transactional
    public EnvioResponseDTO cambiarEstado(Integer id, CambioEstadoDTO d) {
        Envio e = envios.findByIdWithRelations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado"));
        String anterior = e.getEstadoEnvio(), nuevo = d.nuevoEstado().trim().toUpperCase();
        if (("ENTREGADO".equals(anterior) || "CANCELADO".equals(anterior))
                && ("PENDIENTE".equals(nuevo) || "EN_TRANSITO".equals(nuevo)))
            throw new InvalidStateTransitionException(
                    "Transición de estado no permitida para el envío " + e.getCodigoRastreo());
        e.setEstadoEnvio(nuevo);
        envios.save(e);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u = usuarios.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        BitacoraEnvio b = new BitacoraEnvio();
        b.setEnvio(e);
        b.setEstadoAnterior(anterior);
        b.setEstadoNuevo(nuevo);
        b.setFechaCambio(LocalDateTime.now());
        b.setUsuario(u);
        b.setObservaciones(d.observaciones());
        bitacoras.save(b);
        return toDto(e);
    }

    public List<BitacoraResponseDTO> bitacora(Integer id) {
        if (!envios.existsById(id))
            throw new ResourceNotFoundException("Envío no encontrado");
        return bitacoras
                .findByEnvioId(id).stream().map(b -> new BitacoraResponseDTO(b.getId(), b.getEstadoAnterior(),
                        b.getEstadoNuevo(), b.getFechaCambio(), b.getUsuario().getUsername(), b.getObservaciones()))
                .toList();
    }

    private EnvioResponseDTO toDto(Envio e) {
        return new EnvioResponseDTO(e.getId(), e.getCodigoRastreo(), e.getDireccionDestino(), e.getPesoKg(),
                e.getCosto(), e.getEstadoEnvio(),
                e.getVehiculo() != null ? e.getVehiculo().getPlaca() : null,
                e.getConductor() != null ? e.getConductor().getNombre() : null);
    }
}
