package cr.ac.ucr.paraiso.ie.c5l811.expresofast.security;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtTokenProvider provider;

    @Mock
    private CustomUserDetailsService users;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @AfterEach
    void limpiar() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_deberiaAutenticarTokenValido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token-prueba");

        when(provider.validateToken("token-prueba"))
                .thenReturn(true);

        when(provider.getUsernameFromToken("token-prueba"))
                .thenReturn("admin");

        UserDetails usuario = User.withUsername("admin")
                .password("password")
                .authorities("ROLE_ADMIN")
                .build();

        when(users.loadUserByUsername("admin"))
                .thenReturn(usuario);

        JwtAuthenticationFilter filter =
                new JwtAuthenticationFilter(provider, users);

        filter.doFilterInternal(request, response, chain);

        assertNotNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        assertEquals(
                "admin",
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noDeberiaAutenticarSinToken() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        JwtAuthenticationFilter filter =
                new JwtAuthenticationFilter(provider, users);

        filter.doFilterInternal(request, response, chain);

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_noDeberiaAutenticarTokenInvalido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token-invalido");

        when(provider.validateToken("token-invalido"))
                .thenReturn(false);

        JwtAuthenticationFilter filter =
                new JwtAuthenticationFilter(provider, users);

        filter.doFilterInternal(request, response, chain);

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        verify(chain).doFilter(request, response);
    }
}

