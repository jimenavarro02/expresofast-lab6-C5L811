package cr.ac.ucr.paraiso.ie.c5l811.expresofast.business;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Conductor;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Envio;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Usuario;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Vehiculo;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.CambioEstadoDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.EnvioRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.exception.InvalidStateTransitionException;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.exception.ResourceNotFoundException;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.BitacoraEnvioRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.ConductorRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.EnvioRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.UsuarioRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.VehiculoRepository;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envios;

    @Mock
    private VehiculoRepository vehiculos;

    @Mock
    private ConductorRepository conductores;

    @Mock
    private UsuarioRepository usuarios;

    @Mock
    private BitacoraEnvioRepository bitacoras;

    @InjectMocks
    private EnvioService envioService;

    private Vehiculo vehiculo;
    private Conductor conductor;
    private Usuario usuario;

    @BeforeEach
    void prepararDatos() {

        vehiculo = new Vehiculo();
        vehiculo.setPlaca("ABC123");

        conductor = new Conductor();
        conductor.setNombre("Juan Pérez");

        usuario = new Usuario();
        usuario.setUsername("admin");
    }

    @AfterEach
    void limpiarSeguridad() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void crear_deberiaCrearEnvioCorrectamente() {

        // Arrange: preparamos los datos de la prueba
        EnvioRequestDTO request = new EnvioRequestDTO(
                "EXP-1234",
                "Cartago",
                new BigDecimal("5.0"),
                new BigDecimal("2500.0"),
                1,
                1
        );

        Envio envioGuardado = new Envio();

        envioGuardado.setId(1);
        envioGuardado.setCodigoRastreo("EXP-1234");
        envioGuardado.setDireccionDestino("Cartago");
        envioGuardado.setPesoKg(new BigDecimal("5.0"));
        envioGuardado.setCosto(new BigDecimal("2500.0"));
        envioGuardado.setEstadoEnvio("PENDIENTE");
        envioGuardado.setVehiculo(vehiculo);
        envioGuardado.setConductor(conductor);

        when(vehiculos.findById(1))
                .thenReturn(Optional.of(vehiculo));

        when(conductores.findById(1))
                .thenReturn(Optional.of(conductor));

        when(envios.save(any(Envio.class)))
                .thenReturn(envioGuardado);

        // Act: ejecutamos el método que estamos probando
        var resultado = envioService.crear(request);

        // Assert: comprobamos el resultado
        assertNotNull(resultado);
        assertEquals("EXP-1234", resultado.codigoRastreo());
        assertEquals("PENDIENTE", resultado.estadoEnvio());
        assertEquals("ABC123", resultado.placaVehiculo());
        assertEquals("Juan Pérez", resultado.nombreConductor());

        verify(envios).save(any(Envio.class));
    }

    @Test
    void crear_deberiaLanzarExcepcionSiNoExisteVehiculo() {

        EnvioRequestDTO request = new EnvioRequestDTO(
                "EXP-1234",
                "Cartago",
                new BigDecimal("5.0"),
                new BigDecimal("2500.0"),
                1,
                1
        );

        // Simulamos que el vehículo no existe
        when(vehiculos.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> envioService.crear(request)
        );

        // Como no existe el vehículo, nunca se debe guardar el envío
        verify(envios, never()).save(any(Envio.class));
    }

    @Test
    void crear_deberiaLanzarExcepcionSiNoExisteConductor() {

        EnvioRequestDTO request = new EnvioRequestDTO(
                "EXP-1234",
                "Cartago",
                new BigDecimal("5.0"),
                new BigDecimal("2500.0"),
                1,
                1
        );

        when(vehiculos.findById(1))
                .thenReturn(Optional.of(vehiculo));

        // Simulamos que el conductor no existe
        when(conductores.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> envioService.crear(request)
        );

        verify(envios, never()).save(any(Envio.class));
    }

    @Test
    void cambiarEstado_deberiaCambiarEstadoCorrectamente() {

        Envio envio = new Envio();

        envio.setId(1);
        envio.setCodigoRastreo("EXP-1234");
        envio.setDireccionDestino("Cartago");
        envio.setPesoKg(new BigDecimal("5.0"));
        envio.setCosto(new BigDecimal("2500.0"));
        envio.setEstadoEnvio("PENDIENTE");
        envio.setVehiculo(vehiculo);
        envio.setConductor(conductor);

        CambioEstadoDTO cambio = new CambioEstadoDTO(
                "EN_TRANSITO",
                "El paquete salió"
        );

        when(envios.findByIdWithRelations(1))
                .thenReturn(Optional.of(envio));

        when(usuarios.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        when(envios.save(any(Envio.class)))
                .thenReturn(envio);

        // Simulamos que el usuario autenticado es "admin"
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        null
                )
        );

        var resultado = envioService.cambiarEstado(1, cambio);

        assertNotNull(resultado);
        assertEquals("EN_TRANSITO", resultado.estadoEnvio());

        verify(envios).save(envio);
        verify(bitacoras).save(any());
    }

    @Test
    void cambiarEstado_deberiaRechazarTransicionInvalida() {

        Envio envio = new Envio();

        envio.setId(1);
        envio.setCodigoRastreo("EXP-1234");
        envio.setEstadoEnvio("ENTREGADO");

        CambioEstadoDTO cambio = new CambioEstadoDTO(
                "PENDIENTE",
                "Intento de cambio"
        );

        when(envios.findByIdWithRelations(1))
                .thenReturn(Optional.of(envio));

        assertThrows(
                InvalidStateTransitionException.class,
                () -> envioService.cambiarEstado(1, cambio)
        );

        // No debe guardar el envío si la transición es inválida
        verify(envios, never()).save(any(Envio.class));

        // Tampoco debe crear una entrada en la bitácora
        verify(bitacoras, never()).save(any());
    }

    @Test
    void cambiarEstado_deberiaLanzarExcepcionSiNoExisteEnvio() {

        CambioEstadoDTO cambio = new CambioEstadoDTO(
                "EN_TRANSITO",
                "Cambio de estado"
        );

        when(envios.findByIdWithRelations(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> envioService.cambiarEstado(1, cambio)
        );
    }

    @Test
    void bitacora_deberiaLanzarExcepcionSiNoExisteEnvio() {

        when(envios.existsById(1))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> envioService.bitacora(1)
        );

        // No debería consultar la bitácora
        verify(bitacoras, never()).findByEnvioId(1);
    }
}
