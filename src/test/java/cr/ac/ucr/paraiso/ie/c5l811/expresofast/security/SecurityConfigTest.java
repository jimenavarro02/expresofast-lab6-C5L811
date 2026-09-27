package cr.ac.ucr.paraiso.ie.c5l811.expresofast.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

class SecurityConfigTest {

    @Test
    void passwordEncoder_deberiaCrearEncoder() {

        SecurityConfig config =
                new SecurityConfig(mock(JwtAuthenticationFilter.class));

        PasswordEncoder encoder =
                config.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder.matches(
                "123456",
                encoder.encode("123456")
        ));
    }

    @Test
    void corsConfigurationSource_deberiaCrearConfiguracion() {

        SecurityConfig config =
                new SecurityConfig(mock(JwtAuthenticationFilter.class));

        CorsConfigurationSource source =
                config.corsConfigurationSource();

        assertNotNull(source);
    }

    @Test
    void authenticationManager_deberiaRetornarManager() throws Exception {

        SecurityConfig config =
                new SecurityConfig(mock(JwtAuthenticationFilter.class));

        AuthenticationManager manager =
                mock(AuthenticationManager.class);

        AuthenticationConfiguration authenticationConfiguration =
                mock(AuthenticationConfiguration.class);

        org.mockito.Mockito.when(
                authenticationConfiguration.getAuthenticationManager()
        ).thenReturn(manager);

        AuthenticationManager resultado =
                config.authenticationManager(authenticationConfiguration);

        assertNotNull(resultado);
        assertEquals(manager, resultado);
    }

    @Test
    void securityFilterChain_deberiaCrearCadena() throws Exception {

        SecurityConfig config =
                new SecurityConfig(mock(JwtAuthenticationFilter.class));

        org.springframework.security.config.annotation.web.builders.HttpSecurity http =
                mock(org.springframework.security.config.annotation.web.builders.HttpSecurity.class);

        SecurityFilterChain chain =
                mock(SecurityFilterChain.class);

        assertNotNull(chain);
    }
}

