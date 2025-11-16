package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// --- IMPORTACIONES AÑADIDAS PARA EL PLAN B ---
import com.example.patas_y_colas.model.Role;
import com.example.patas_y_colas.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;
// --- FIN DE IMPORTACIONES AÑADIDAS ---

@RestController
@RequestMapping("/api/users") // <-- ¡ESTA ES LA RUTA CORRECTA!
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // --- DEPENDENCIA AÑADIDA PARA EL PLAN B ---
    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // --- MÉTODO SECRETO TEMPORAL AÑADIDO (PLAN B) ---
    /**
     * Endpoint temporal para promover un usuario a ADMIN.
     * Uso: /api/users/promote-admin?email=correo@ejemplo.com&key=miClaveSecreta123
     */
    @GetMapping("/promote-admin")
    public ResponseEntity<String> promoteAdmin(
            @RequestParam("email") String email, 
            @RequestParam("key") String key
    ) {
        // Clave de seguridad simple para que no cualquiera pueda usar esto
        if (!key.equals("miClaveSecreta123")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Clave incorrecta");
        }

        // Buscamos al usuario en la BD de la nube
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + email));
        
        // Lo promovemos a ADMIN
        user.setRole(Role.ROLE_ADMIN);
        usuarioRepository.save(user);
        
        return ResponseEntity.ok("¡ÉXITO! El usuario " + email + " ahora es ROLE_ADMIN.");
    }
}