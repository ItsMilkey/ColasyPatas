package com.example.patas_y_colas.dtos;

import com.example.patas_y_colas.model.Role; // 1. IMPORTACIÓN AÑADIDA
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    // Esta es la respuesta que enviaremos al frontend (el token)
    private String token;
    
    // 2. CAMPO DE ROL AÑADIDO
    private Role role; 
}