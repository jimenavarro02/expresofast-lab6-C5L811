package cr.ac.ucr.paraiso.ie.c5l811.expresofast.business;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Rol;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Usuario;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.AuthRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.AuthResponseDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.UsuarioRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.security.JwtTokenProvider;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager manager;

    @Mock
    private JwtTokenProvider jwt;

    @Mock
    private UsuarioRepository users;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_deberiaAutenticarYRetornarToken() {

        Usuario usuario = new Usuario();
        usuario.setUsername("admin");

        Rol rol = new Rol();
        rol.setNombreRol("ADMIN");

        usuario.setRoles(Set.of(rol));

        AuthRequestDTO request = new AuthRequestDTO(
                "admin",
                "123456"
        );

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwt.generateToken(authentication))
                .thenReturn("token-prueba");

        when(users.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        when(jwt.getExpirationMs())
                .thenReturn(3600000L);

        AuthResponseDTO resultado = authService.login(request);

        assertNotNull(resultado);
        assertEquals("token-prueba", resultado.token());
        assertEquals("admin", resultado.username());
        assertEquals(Set.of("ADMIN"), resultado.roles());

        verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwt).generateToken(authentication);
        verify(users).findByUsername("admin");
    }

    @Test
    void login_deberiaLanzarExcepcionSiNoExisteUsuario() {

        AuthRequestDTO request = new AuthRequestDTO(
                "admin",
                "123456"
        );

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwt.generateToken(authentication))
                .thenReturn("token-prueba");

        when(users.findByUsername("admin"))
                .thenReturn(Optional.empty());

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> authService.login(request)
        );

        verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwt).generateToken(authentication);
        verify(users).findByUsername("admin");
    }
}

