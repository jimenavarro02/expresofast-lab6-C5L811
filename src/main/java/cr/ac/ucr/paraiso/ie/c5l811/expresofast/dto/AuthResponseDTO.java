package cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto;

import java.util.Set;

public record AuthResponseDTO(String token, String username, Set<String> roles, long expirationTime) {
}
