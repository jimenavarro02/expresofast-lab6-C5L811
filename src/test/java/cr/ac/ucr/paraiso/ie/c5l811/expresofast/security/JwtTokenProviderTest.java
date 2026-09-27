package cr.ac.ucr.paraiso.ie.c5l811.expresofast.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class JwtTokenProviderTest {

    private JwtTokenProvider provider;

    @BeforeEach
    void preparar() {
        provider = new JwtTokenProvider(
                "esta-es-una-clave-secreta-muy-larga-para-jwt-123456789",
                3600000L
        );
    }

    @Test
    void generateToken_deberiaCrearToken() {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken("admin", null);

        String token = provider.generateToken(authentication);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals("admin", provider.getUsernameFromToken(token));
    }

    @Test
    void validateToken_deberiaRetornarTrueParaTokenValido() {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken("admin", null);

        String token = provider.generateToken(authentication);

        assertTrue(provider.validateToken(token));
    }

    @Test
    void validateToken_deberiaRetornarFalseParaTokenInvalido() {

        assertFalse(provider.validateToken("token-invalido"));
    }

    @Test
    void getExpirationMs_deberiaRetornarExpiracion() {

        assertEquals(3600000L, provider.getExpirationMs());
    }
}

