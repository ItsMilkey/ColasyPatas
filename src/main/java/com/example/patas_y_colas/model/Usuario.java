package com.example.patas_y_colas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un usuario del sistema")
public class Usuario {

    @Id
    @Column(name = "ID_USUARIO", unique = true)
    @Schema(description = "RUT del usuario (identificador único)", example = "12.345.678-9")
    private String rut;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String email;

    @Column(name = "CONTRASENA_HASH", nullable = false, length = 100)
    @Schema(description = "Contraseña encriptada del usuario", example = "$2a$10$abc123...")
    private String contrasena;

    @Column(name = "ROL")
    @Schema(description = "Rol del usuario en el sistema", example = "admin")
    private String rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(description = "Lista de perros registrados por este usuario")
    private List<Perro> perros;

    public boolean esAdministrador() {
        return this.rol != null && this.rol.equalsIgnoreCase("admin");
    }

    public boolean validarCredenciales(String correo, String contrasena) {
        return this.email.equals(correo) && this.contrasena.equals(contrasena);
    }
}
