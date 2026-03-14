package com.lashes.lashes_backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if ("Credenciales incorrectas".equals(message) || "Usuario inactivo".equals(message)) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (message != null && (message.contains("no encontrado") || message.contains("no está disponible"))) {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(Map.of("message", message != null ? message : "Error"));
    }
}
