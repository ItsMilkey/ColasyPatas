package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Dieta;
import com.example.patas_y_colas.service.DietaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dietas")
@Tag(name = "Dieta", description = "Operaciones relacionadas con las dietas de los perros")
public class DietaController {

    @Autowired
    private DietaService dietaService;

    @Operation(summary = "Obtener todas las dietas")
    @ApiResponse(responseCode = "200", description = "Lista de dietas obtenida con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dieta.class)))
    @GetMapping
    public ResponseEntity<List<Dieta>> obtenerTodas() {
        List<Dieta> dietas = dietaService.findAll();
        return dietas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(dietas);
    }

    @Operation(summary = "Obtener una dieta por su ID")
    @ApiResponse(responseCode = "200", description = "Dieta encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dieta.class)))
    @ApiResponse(responseCode = "404", description = "Dieta no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Dieta> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(dietaService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Guardar una nueva dieta")
    @ApiResponse(responseCode = "201", description = "Dieta creada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dieta.class)))
    @PostMapping
    public ResponseEntity<Dieta> guardarDieta(@RequestBody Dieta dieta) {
        Dieta dietaGuardada = dietaService.save(dieta);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietaGuardada);
    }

    @Operation(summary = "Actualizar una dieta existente")
    @ApiResponse(responseCode = "200", description = "Dieta actualizada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dieta.class)))
    @ApiResponse(responseCode = "404", description = "Dieta no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Dieta> actualizarDieta(@PathVariable int id, @RequestBody Dieta dieta) {
        try {
            dieta.setIdDieta(id);
            return ResponseEntity.ok(dietaService.save(dieta));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una dieta por su ID")
    @ApiResponse(responseCode = "204", description = "Dieta eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Dieta no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDieta(@PathVariable int id) {
        try {
            dietaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Verificar si una dieta está activa")
    @ApiResponse(responseCode = "200", description = "Estado de la dieta retornado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @GetMapping("/{id}/activa")
    public ResponseEntity<Boolean> dietaActiva(@PathVariable int id) {
        try {
            return ResponseEntity.ok(dietaService.findById(id).dietaActiva());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar la descripción de una dieta")
    @ApiResponse(responseCode = "200", description = "Descripción actualizada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dieta.class)))
    @ApiResponse(responseCode = "404", description = "Dieta no encontrada")
    @PutMapping("/{id}/descripcion")
    public ResponseEntity<Dieta> actualizarDescripcion(
            @PathVariable int id,
            @RequestParam String nuevaDescripcion) {
        try {
            Dieta dietaActualizada = dietaService.actualizarDescripcion(id, nuevaDescripcion);
            return ResponseEntity.ok(dietaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
