package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// --- IMPORTACIONES DEL PLAN B ELIMINADAS ---
// import com.example.patas_y_colas.model.Role;
// import com.example.patas_y_colas.repository.UsuarioRepository;
// import jakarta.persistence.EntityNotFoundException;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users") // <-- ¡ESTA ES LA RUTA CORRECTA!
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // --- DEPENDENCIA DEL PLAN B ELIMINADA ---
    // @Autowired
    // private UsuarioRepository usuarioRepository;

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

    // --- MÉTODO SECRETO TEMPORAL (PLAN B) ELIMINADO ---
}