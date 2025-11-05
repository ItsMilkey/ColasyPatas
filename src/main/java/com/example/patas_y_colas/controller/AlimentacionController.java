package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Alimentacion;
import com.example.patas_y_colas.service.AlimentacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimentaciones")
@Tag(name = "Alimentación", description = "Operaciones relacionadas con la alimentación de los perros")
public class AlimentacionController {

    @Autowired
    private AlimentacionService alimentacionService;

    @Operation(summary = "Listar todas las alimentaciones")
    @ApiResponse(responseCode = "200", description = "Lista de alimentaciones obtenida con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alimentacion.class)))
    @GetMapping
    public ResponseEntity<List<Alimentacion>> listar() {
        List<Alimentacion> lista = alimentacionService.findAll();
        return lista.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar alimentación por ID")
    @ApiResponse(responseCode = "200", description = "Alimentación encontrada",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alimentacion.class)))
    @ApiResponse(responseCode = "404", description = "Alimentación no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Alimentacion> buscarPorId(@PathVariable int id) {
        try {
            Alimentacion alimentacion = alimentacionService.findById(id);
            return ResponseEntity.ok(alimentacion);
        } catch (EntityNotFoundException | java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva alimentación")
    @ApiResponse(responseCode = "201", description = "Alimentación creada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alimentacion.class)))
    @PostMapping
    public ResponseEntity<Alimentacion> crear(@RequestBody Alimentacion alimentacion) {
        Alimentacion creada = alimentacionService.save(alimentacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Actualizar alimentación por ID")
    @ApiResponse(responseCode = "200", description = "Alimentación actualizada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alimentacion.class)))
    @ApiResponse(responseCode = "404", description = "Alimentación no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Alimentacion> actualizar(@PathVariable int id, @RequestBody Alimentacion alimentacion) {
        try {
            Alimentacion existente = alimentacionService.findById(id);
            existente.setPorcion(alimentacion.getPorcion());
            existente.setHorario(alimentacion.getHorario());
            existente.setPerro(alimentacion.getPerro());
            Alimentacion actualizada = alimentacionService.save(existente);
            return ResponseEntity.ok(actualizada);
        } catch (EntityNotFoundException | java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar alimentación por ID")
    @ApiResponse(responseCode = "204", description = "Alimentación eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Alimentación no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            alimentacionService.findById(id); // Verifica existencia
            alimentacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException | java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
