package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.dtos.AuthResponseDTO;
import com.example.patas_y_colas.dtos.LoginDTO;
import com.example.patas_y_colas.dtos.RegisterDTO;
import com.example.patas_y_colas.service.AuthService;
// import lombok.RequiredArgsConstructor; // <-- ELIMINADO
import org.springframework.beans.factory.annotation.Autowired; // <-- AÑADIDO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Ruta pública para autenticación
// @RequiredArgsConstructor // <-- ELIMINADO
public class AuthController {

    private final AuthService authService;

    // --- CONSTRUCTOR CON @AUTOWIRED AÑADIDO ---
    // Esto es más robusto para la inyección de dependencias
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para Iniciar Sesión (Login)
     * Tu LoginPage.jsx llamará a este endpoint.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO request) {
        // El servicio maneja toda la lógica y devuelve el token y el rol
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Endpoint para Registrarse (Register)
     * Tu RegisterPage.jsx llamará a este endpoint.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO request) {
        // El servicio maneja la encriptación, asignación de rol y devuelve token/rol
        return ResponseEntity.ok(authService.register(request));
    }
}