package com.example.patas_y_colas.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    // Cambiado de "correo" a "email"
    private String email; 
    
    // Cambiado de "contrasena" a "password"
    private String password; 
}