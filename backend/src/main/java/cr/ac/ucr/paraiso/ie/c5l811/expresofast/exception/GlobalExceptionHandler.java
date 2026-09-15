package cr.ac.ucr.paraiso.ie.c5l811.expresofast.exception;

import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "VALIDATION_ERROR", "messages", errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<?> notFound(ResourceNotFoundException e) {
        return ResponseEntity.status(404).body(Map.of("status", 404, "error", "NOT_FOUND", "message", e.getMessage()));
    }

    @ExceptionHandler(InvalidStateTransitionException.class)
    ResponseEntity<?> invalid(InvalidStateTransitionException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("status", 400, "error", "INVALID_STATE_TRANSITION", "message", e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<?> badCredentials() {
        return ResponseEntity.status(401)
                .body(Map.of("status", 401, "error", "UNAUTHORIZED", "message", "Credenciales inválidas"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<?> denied() {
        return ResponseEntity.status(403).body(Map.of("status", 403, "error", "FORBIDDEN", "message",
                "No tiene permisos para realizar esta operación"));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception e) {
        return ResponseEntity.status(500)
                .body(Map.of("status", 500, "error", "INTERNAL_SERVER_ERROR", "message", "Ocurrió un error interno"));
    }
}
