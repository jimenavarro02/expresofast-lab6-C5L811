package cr.ac.ucr.paraiso.ie.c5l811.expresofast.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Usuario;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;

    public CustomUserDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (!Boolean.TRUE.equals(u.getActivo()))
            throw new DisabledException("Usuario inactivo");
        return User.withUsername(u.getUsername()).password(u.getPasswordHash())
                .authorities(u.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getNombreRol())).toList())
                .build();
    }
}
