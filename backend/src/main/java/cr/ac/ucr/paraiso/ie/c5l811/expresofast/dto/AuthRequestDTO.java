package cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(@NotBlank String username, @NotBlank String password) {
}
