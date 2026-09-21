package cr.ac.ucr.paraiso.ie.c5l811.expresofast.controller;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.business.EnvioService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.BitacoraResponseDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.CambioEstadoDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.EnvioRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.EnvioResponseDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.security.CustomUserDetailsService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.security.JwtTokenProvider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnvioController.class)
@AutoConfigureMockMvc(addFilters = false)
class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void optimizados_deberiaRetornarListaDeEnvios() throws Exception {

        EnvioResponseDTO envio = new EnvioResponseDTO(
                1,
                "EXP-1234",
                "Cartago",
                new BigDecimal("5.0"),
                new BigDecimal("2500.0"),
                "PENDIENTE",
                "ABC123",
                "Juan Pérez"
        );

        when(envioService.optimizados())
                .thenReturn(List.of(envio));

        mockMvc.perform(
                get("/api/envios/optimizados")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].codigoRastreo").value("EXP-1234"))
        .andExpect(jsonPath("$[0].estadoEnvio").value("PENDIENTE"));
    }

    @Test
    void crear_deberiaCrearEnvioCorrectamente() throws Exception {

        EnvioResponseDTO respuesta = new EnvioResponseDTO(
                1,
                "EXP-1234",
                "Cartago",
                new BigDecimal("5.0"),
                new BigDecimal("2500.0"),
                "PENDIENTE",
                "ABC123",
                "Juan Pérez"
        );

        when(envioService.crear(any(EnvioRequestDTO.class)))
                .thenReturn(respuesta);

        String json = """
                {
                    "codigoRastreo": "EXP-1234",
                    "direccionDestino": "Cartago",
                    "pesoKg": 5.0,
                    "costo": 2500.0,
                    "vehiculoId": 1,
                    "conductorId": 1
                }
                """;

        mockMvc.perform(
                post("/api/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.codigoRastreo").value("EXP-1234"))
        .andExpect(jsonPath("$.estadoEnvio").value("PENDIENTE"));
    }

    @Test
    void crear_deberiaRetornar400CuandoLosDatosSonInvalidos() throws Exception {

        String json = """
                {
                    "codigoRastreo": "",
                    "direccionDestino": "",
                    "pesoKg": 0,
                    "costo": 0,
                    "vehiculoId": null,
                    "conductorId": null
                }
                """;

        mockMvc.perform(
                post("/api/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void estado_deberiaCambiarEstadoCorrectamente() throws Exception {

        EnvioResponseDTO respuesta = new EnvioResponseDTO(
                1,
                "EXP-1234",
                "Cartago",
                new BigDecimal("5.0"),
                new BigDecimal("2500.0"),
                "EN_TRANSITO",
                "ABC123",
                "Juan Pérez"
        );

        when(envioService.cambiarEstado(
                eq(1),
                any(CambioEstadoDTO.class)
        )).thenReturn(respuesta);

        String json = """
                {
                    "nuevoEstado": "EN_TRANSITO",
                    "observaciones": "El paquete salió"
                }
                """;

        mockMvc.perform(
                patch("/api/envios/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.estadoEnvio").value("EN_TRANSITO"))
        .andExpect(jsonPath("$.codigoRastreo").value("EXP-1234"));
    }

    @Test
    void estado_deberiaRetornar400CuandoElEstadoEstaVacio() throws Exception {

        String json = """
                {
                    "nuevoEstado": "",
                    "observaciones": "Prueba"
                }
                """;

        mockMvc.perform(
                patch("/api/envios/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void bitacora_deberiaRetornarListaCorrectamente() throws Exception {

        BitacoraResponseDTO registro = new BitacoraResponseDTO(
                1,
                "PENDIENTE",
                "EN_TRANSITO",
                LocalDateTime.of(2026, 9, 20, 18, 30),
                "admin",
                "El paquete salió"
        );

        when(envioService.bitacora(1))
                .thenReturn(List.of(registro));

        mockMvc.perform(
                get("/api/envios/1/bitacora")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].estadoAnterior").value("PENDIENTE"))
        .andExpect(jsonPath("$[0].estadoNuevo").value("EN_TRANSITO"))
        .andExpect(jsonPath("$[0].usuario").value("admin"))
        .andExpect(jsonPath("$[0].observaciones").value("El paquete salió"));
    }
}

