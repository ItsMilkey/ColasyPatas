package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.dtos.AuthResponseDTO;
import com.example.patas_y_colas.dtos.LoginDTO;
import com.example.patas_y_colas.dtos.RegisterDTO;
import com.example.patas_y_colas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Ruta pública para autenticación
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para Iniciar Sesión (Login)
     * Tu LoginPage.jsx llamará a este endpoint.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO request) {
        // El servicio maneja toda la lógica y devuelve el token
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Endpoint para Registrarse (Register)
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO request) {
        // El servicio maneja toda la lógica y devuelve el token
        return ResponseEntity.ok(authService.register(request));
    }
}