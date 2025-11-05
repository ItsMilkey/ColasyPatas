package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.dtos.RangoPesoDTO;
import com.example.patas_y_colas.model.Peso;
import com.example.patas_y_colas.service.PesoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pesos")
@Tag(name = "Pesos", description = "Operaciones relacionadas con los registros de peso de los perros")
public class PesoController {

    @Autowired
    private PesoService pesoService;

    @GetMapping
    @Operation(summary = "Obtener todos los registros de peso", description = "Devuelve una lista de todos los registros de peso")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Peso.class))))
    public ResponseEntity<List<Peso>> obtenerTodos() {
        return ResponseEntity.ok(pesoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un registro de peso por ID", description = "Devuelve un registro de peso específico según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro de peso encontrado",
            content = @Content(schema = @Schema(implementation = Peso.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Peso> obtenerPorId(@PathVariable int id) {
        return ResponseEntity.ok(pesoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo peso", description = "Agrega un nuevo registro de peso para un perro")
    @ApiResponse(responseCode = "201", description = "Registro creado correctamente",
        content = @Content(schema = @Schema(implementation = Peso.class)))
    public ResponseEntity<Peso> guardarPeso(
        @RequestBody(description = "Datos del nuevo registro de peso", required = true,
            content = @Content(schema = @Schema(implementation = Peso.class),
                examples = @ExampleObject(value = "{\"fecha\":\"2025-05-31\",\"pesoKg\":14.8,\"perro\":{\"idPerro\":1}}")))
        @org.springframework.web.bind.annotation.RequestBody Peso peso) {
        return ResponseEntity.ok(pesoService.save(peso));
    }

    @PutMapping
    @Operation(summary = "Actualizar un registro de peso", description = "Modifica los datos de un registro de peso existente")
    @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente",
        content = @Content(schema = @Schema(implementation = Peso.class)))
    public ResponseEntity<Peso> actualizarPeso(
        @RequestBody(description = "Datos actualizados del registro de peso", required = true,
            content = @Content(schema = @Schema(implementation = Peso.class)))
        @org.springframework.web.bind.annotation.RequestBody Peso peso) {
        return ResponseEntity.ok(pesoService.update(peso));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de peso", description = "Elimina un registro de peso según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<String> eliminarPeso(@PathVariable int id) {
        pesoService.delete(id);
        return ResponseEntity.ok("Peso eliminado");
    }

    @PostMapping("/{id}/es-normal")
    @Operation(summary = "Verificar si un peso está dentro de un rango normal", description = "Comprueba si el peso registrado está entre los valores mínimo y máximo especificados")
    @ApiResponse(responseCode = "200", description = "Resultado de la verificación",
        content = @Content(schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<Boolean> verificarPesoNormal(
        @PathVariable int id,
        @RequestBody(description = "Rango mínimo y máximo para verificar el peso",
            content = @Content(schema = @Schema(implementation = RangoPesoDTO.class),
                examples = @ExampleObject(value = "{\"rangoMin\": 10.0, \"rangoMax\": 15.0}")))
        @org.springframework.web.bind.annotation.RequestBody RangoPesoDTO rango) {
        return ResponseEntity.ok(pesoService.esPesoNormal(id, rango.getRangoMin(), rango.getRangoMax()));
    }
}
