package com.example.patas_y_colas.dtos;

import com.example.patas_y_colas.model.Role;

// Quitamos las anotaciones de Lombok para evitar errores de compilación silenciosos
public class AuthResponseDTO {

    private String token;
    private Role role;
    private String message;

    // 1. Constructor Vacío (Necesario para Spring)
    public AuthResponseDTO() {
    }

    // 2. Constructor con todos los campos (El que usas en AuthService)
    public AuthResponseDTO(String token, Role role, String message) {
        this.token = token;
        this.role = role;
        this.message = message;
    }

    // 3. GETTERS (Vitales para que se vea el JSON en Postman)
    public String getToken() {
        return token;
    }

    public Role getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    // 4. SETTERS
    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}