package cr.ac.ucr.paraiso.ie.c5l811.expresofast.controller;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.business.AuthService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.AuthRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.AuthResponseDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.security.CustomUserDetailsService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.security.JwtTokenProvider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    // Mock necesario porque Spring Security lo utiliza.
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    // Mock necesario porque JwtAuthenticationFilter
    // depende de este servicio.
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void login_deberiaRetornarTokenCuandoLosDatosSonCorrectos() throws Exception {

        AuthResponseDTO respuesta = new AuthResponseDTO(
                "token-prueba",
                "admin",
                Set.of("ADMIN"),
                3600000L
        );

        when(authService.login(any(AuthRequestDTO.class)))
                .thenReturn(respuesta);

        String json = """
                {
                    "username": "admin",
                    "password": "123456"
                }
                """;

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("token-prueba"))
        .andExpect(jsonPath("$.username").value("admin"));
    }

    @Test
    void login_deberiaRetornar400CuandoLosDatosEstanVacios() throws Exception {

        String json = """
                {
                    "username": "",
                    "password": ""
                }
                """;

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }
}

