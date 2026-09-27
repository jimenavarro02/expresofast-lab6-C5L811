package cr.ac.ucr.paraiso.ie.c5l811.expresofast.business;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.domain.Usuario;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.AuthRequestDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.AuthResponseDTO;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.repository.UsuarioRepository;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.security.JwtTokenProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager manager;
    private final JwtTokenProvider jwt;
    private final UsuarioRepository users;

    public AuthService(
            AuthenticationManager manager,
            JwtTokenProvider jwt,
            UsuarioRepository users) {

        this.manager = manager;
        this.jwt = jwt;
        this.users = users;
    }

    public AuthResponseDTO login(AuthRequestDTO request) {

        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()));

        String token = jwt.generateToken(auth);

        Usuario u = users.findByUsername(request.username())
                .orElseThrow();

        return new AuthResponseDTO(
                token,
                u.getUsername(),
                u.getRoles()
                        .stream()
                        .map(r -> r.getNombreRol())
                        .collect(Collectors.toSet()),
                System.currentTimeMillis() + jwt.getExpirationMs());
    }
}