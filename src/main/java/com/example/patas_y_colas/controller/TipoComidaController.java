package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.TipoComida;
import com.example.patas_y_colas.service.TipoComidaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-comida")
@Tag(name = "Tipos de Comida", description = "Operaciones para gestionar los tipos de comida de los perros")
public class TipoComidaController {

    @Autowired
    private TipoComidaService tipoComidaService;

    @GetMapping
    @Operation(summary = "Listar todos los tipos de comida", description = "Devuelve una lista de todos los tipos de comida registrados")
    @ApiResponse(responseCode = "200", description = "Lista de tipos de comida",
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TipoComida.class))))
    public List<TipoComida> obtenerTodos() {
        return tipoComidaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de comida por ID", description = "Devuelve un tipo de comida específico según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de comida encontrado",
            content = @Content(schema = @Schema(implementation = TipoComida.class))),
        @ApiResponse(responseCode = "404", description = "Tipo de comida no encontrado")
    })
    public TipoComida obtenerPorId(
        @io.swagger.v3.oas.annotations.Parameter(description = "ID del tipo de comida") @PathVariable int id) {
        return tipoComidaService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo tipo de comida", description = "Agrega un nuevo tipo de comida al sistema")
    @ApiResponse(responseCode = "201", description = "Tipo de comida creado",
        content = @Content(schema = @Schema(implementation = TipoComida.class)))
    public TipoComida guardar(
        @RequestBody(description = "Datos del tipo de comida a registrar", required = true,
            content = @Content(schema = @Schema(implementation = TipoComida.class),
                examples = @ExampleObject(value = "{\"nombre\": \"Alimento húmedo dietético\", \"perro\": {\"idPerro\": 1}}")
            )
        )
        @org.springframework.web.bind.annotation.RequestBody TipoComida tipoComida) {
        return tipoComidaService.save(tipoComida);
    }

    @PutMapping
    @Operation(summary = "Actualizar tipo de comida", description = "Modifica los datos de un tipo de comida existente")
    @ApiResponse(responseCode = "200", description = "Tipo de comida actualizado",
        content = @Content(schema = @Schema(implementation = TipoComida.class)))
    public TipoComida actualizar(
        @RequestBody(description = "Datos actualizados del tipo de comida",
            content = @Content(schema = @Schema(implementation = TipoComida.class)))
        @org.springframework.web.bind.annotation.RequestBody TipoComida tipoComida) {
        return tipoComidaService.update(tipoComida);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de comida", description = "Elimina un tipo de comida según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de comida eliminado"),
        @ApiResponse(responseCode = "404", description = "Tipo de comida no encontrado")
    })
    public String eliminar(
        @io.swagger.v3.oas.annotations.Parameter(description = "ID del tipo de comida a eliminar") @PathVariable int id) {
        tipoComidaService.delete(id);
        return "Tipo de comida con ID " + id + " eliminado correctamente.";
    }
}
