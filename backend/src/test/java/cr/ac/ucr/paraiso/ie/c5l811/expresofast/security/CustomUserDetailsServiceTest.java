package cr.ac.ucr.paraiso.ie.c5l811.expresofast.security;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Rol;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Usuario;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_deberiaRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPasswordHash("password");
        usuario.setActivo(true);

        Rol rol = new Rol();
        rol.setNombreRol("ROLE_ADMIN");

        usuario.setRoles(Set.of(rol));

        when(repo.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        UserDetails resultado =
                service.loadUserByUsername("admin");

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());
        assertEquals("password", resultado.getPassword());
        assertTrue(resultado.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_deberiaLanzarExcepcionSiNoExiste() {

        when(repo.findByUsername("admin"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("admin")
        );
    }

    @Test
    void loadUserByUsername_deberiaLanzarExcepcionSiEstaInactivo() {

        Usuario usuario = new Usuario();
        usuario.setUsername("admin");
        usuario.setPasswordHash("password");
        usuario.setActivo(false);
        usuario.setRoles(Set.of());

        when(repo.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        assertThrows(
                DisabledException.class,
                () -> service.loadUserByUsername("admin")
        );
    }
}

