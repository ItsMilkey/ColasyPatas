package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo usuario",
        description = "Registra un nuevo usuario en el sistema"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos del usuario a registrar",
        required = true,
        content = @Content(
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = "{\"rut\": \"12.345.678-9\", \"nombre\": \"Juan Pérez\", \"email\": \"juan@example.com\", \"contrasena\": \"1234\", \"rol\": \"admin\"}")
        )
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    public ResponseEntity<Usuario> guardar(@org.springframework.web.bind.annotation.RequestBody Usuario usuario) {
        System.out.println("🟢 Recibido usuario: " + usuario);
        System.out.println("🔍 RUT recibido: " + usuario.getRut());

        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/{rut}")
    @Operation(summary = "Buscar usuario por RUT", description = "Obtiene los datos de un usuario específico usando su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> buscar(
        @Parameter(description = "RUT del usuario a buscar", required = true)
        @PathVariable String rut) {
        try {
            Usuario usuario = usuarioService.findByRut(rut);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{rut}")
    @Operation(
        summary = "Actualizar usuario",
        description = "Modifica los datos de un usuario existente usando su RUT",
        requestBody = @RequestBody(
            description = "Datos actualizados del usuario",
            required = true,
            content = @Content(schema = @Schema(implementation = Usuario.class))
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizar(
        @Parameter(description = "RUT del usuario a actualizar", required = true)
        @PathVariable String rut,
        @org.springframework.web.bind.annotation.RequestBody Usuario usuario) {
        try {
            Usuario usu = usuarioService.findByRut(rut);
            usu.setRut(rut);
            usu.setNombre(usuario.getNombre());
            usu.setEmail(usuario.getEmail());
            usu.setContrasena(usuario.getContrasena());
            usu.setRol(usuario.getRol());

            usuarioService.save(usu);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{rut}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario específico usando su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "RUT del usuario a eliminar", required = true)
        @PathVariable String rut) {
        try {
            usuarioService.delete(rut);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
