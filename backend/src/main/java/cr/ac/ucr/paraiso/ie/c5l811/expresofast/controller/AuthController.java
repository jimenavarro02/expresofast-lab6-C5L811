package cr.ac.ucr.paraiso.ie.c5l811.expresofast.controller;

import cr.ac.ucr.paraiso.ie.c5l811.expresofast.business.AuthService;
import cr.ac.ucr.paraiso.ie.c5l811.expresofast.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService s) {
        service = s;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO dto) {
        return service.login(dto);
    }
}
