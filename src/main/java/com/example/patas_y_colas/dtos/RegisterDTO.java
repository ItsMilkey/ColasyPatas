package com.example.patas_y_colas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    // Opcional: podrías añadir un campo 'role' si quisieras que el admin lo setee
}