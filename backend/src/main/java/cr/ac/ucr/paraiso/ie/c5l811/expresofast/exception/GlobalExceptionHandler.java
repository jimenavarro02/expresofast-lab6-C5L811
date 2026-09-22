package cr.ac.ucr.paraiso.ie.c5l811.expresofast.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
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

    @ExceptionHandler({ DataIntegrityViolationException.class, TransactionSystemException.class })
    ResponseEntity<?> dataIntegrity(RuntimeException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("status", 400, "error", "DATA_INTEGRITY", "message", resolveDataMessage(e)));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception e) {
        log.error("Error no controlado", e);
        String message = resolveDataMessage(e);
        if (!"No fue posible guardar el registro.".equals(message)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", 400, "error", "DATA_INTEGRITY", "message", message));
        }
        return ResponseEntity.status(500)
                .body(Map.of("status", 500, "error", "INTERNAL_SERVER_ERROR", "message", "Ocurrió un error interno"));
    }

    private static String resolveDataMessage(Throwable error) {
        Throwable current = error;
        while (current != null) {
            String msg = current.getMessage();
            if (msg != null) {
                String lower = msg.toLowerCase();
                if (lower.contains("placa") || lower.contains("unique") || lower.contains("duplicate")) {
                    return "Ya existe un vehículo con esa placa.";
                }
                if (lower.contains("empresa") || lower.contains("fk_vehiculo_empresa")) {
                    return "La empresa indicada no existe.";
                }
                if (lower.contains("ck_vehiculo_estado")) {
                    return "El estado del vehículo no es válido.";
                }
            }
            current = current.getCause();
        }
        return "No fue posible guardar el registro.";
    }
}
