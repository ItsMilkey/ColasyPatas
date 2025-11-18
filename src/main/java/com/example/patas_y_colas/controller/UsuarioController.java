package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.UsuarioRepository; // <-- IMPORTACIÓN AÑADIDA
import com.example.patas_y_colas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // <-- IMPORTACIÓN AÑADIDA
import java.util.List;

@RestController
@RequestMapping("/api/users") // <-- ¡ESTA ES LA RUTA CORRECTA!
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // --- DEPENDENCIA AÑADIDA PARA EL PERFIL ---
    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- NUEVO ENDPOINT: OBTENER MI PROPIO PERFIL ---
    // (GET /api/users/me)
    @GetMapping("/me")
    public ResponseEntity<Usuario> getMyProfile(Principal principal) {
        // 'principal.getName()' obtiene el email del token JWT
        String email = principal.getName();
        
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return ResponseEntity.ok(usuario);
    }
    // ------------------------------------------------

    // Endpoint para LISTAR (GET /api/users)
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        // Esto siempre devolverá 200 OK.
        // Si la lista está vacía, enviará un array vacío "[]".
        // Si la lista tiene datos, enviará los datos.
        return ResponseEntity.ok(usuarios); 
    }

    // Endpoint para AGREGAR (POST /api/users)
    // (Esto es para que un ADMIN cree un usuario, diferente del registro público)
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.saveUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Endpoint para ELIMINAR (DELETE /api/users/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            usuarioService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}