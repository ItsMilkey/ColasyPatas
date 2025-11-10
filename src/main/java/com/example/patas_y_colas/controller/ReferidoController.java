package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Referido;
import com.example.patas_y_colas.service.ReferidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referrals") // La ruta que definimos en el frontend
public class ReferidoController {

    @Autowired
    private ReferidoService referidoService;

    /**
     * Endpoint para LISTAR todos los códigos (GET /api/referrals)
     */
    @GetMapping
    public ResponseEntity<List<Referido>> getAllReferidos() {
        List<Referido> referidos = referidoService.getAllReferidos();
        return ResponseEntity.ok(referidos); // Devuelve [] si está vacío
    }

    /**
     * Endpoint para AGREGAR un código (POST /api/referrals)
     */
    @PostMapping
    public ResponseEntity<Referido> createReferido(@RequestBody Referido referido) {
        Referido nuevoReferido = referidoService.saveReferido(referido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReferido);
    }

    /**
     * Endpoint para ELIMINAR un código (DELETE /api/referrals/{id})
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReferido(@PathVariable Long id) {
        try {
            referidoService.deleteReferido(id);
            return ResponseEntity.noContent().build(); // 204 OK
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}