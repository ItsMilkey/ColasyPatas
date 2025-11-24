package com.example.patas_y_colas.dtos;

import com.example.patas_y_colas.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    // Token JWT
    private String token;
    
    // Rol del usuario (ADMIN o USER)
    private Role role; 

    // 3. CAMPO DE MENSAJE AÑADIDO
    private String message;
}