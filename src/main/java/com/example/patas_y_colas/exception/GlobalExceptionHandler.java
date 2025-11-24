package com.example.patas_y_colas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura excepciones generales (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error en la solicitud");
        errorResponse.put("message", ex.getMessage()); // Aquí irá el mensaje de "Email en uso"
        
        // Devuelve 400 BAD REQUEST en lugar de 200 OK
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}