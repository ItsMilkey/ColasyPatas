package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Vacuna;
import com.example.patas_y_colas.service.VacunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacunas")
@Tag(name = "Vacunas", description = "Operaciones relacionadas con las vacunas de la mascota")
public class VacunaController {

    @Autowired
    private VacunaService vacunaService;

    @Operation(summary = "Obtener todas las vacunas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de vacunas",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vacuna.class),
                examples = @ExampleObject(name = "EjemploVacunas", value = "[{\"idVacuna\":\"VAC001\",\"nombre\":\"Antirrábica\",\"descripcion\":\"Vacuna contra la rabia\",\"obligatoria\":true}]")
            ))
    })
    @GetMapping
    public List<Vacuna> obtenerTodas() {
        return vacunaService.findAll();
    }

    @Operation(summary = "Obtener una vacuna por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vacuna encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vacuna.class),
                examples = @ExampleObject(name = "EjemploVacuna", value = "{\"idVacuna\":\"VAC001\",\"nombre\":\"Antirrábica\",\"descripcion\":\"Vacuna contra la rabia\",\"obligatoria\":true}")
            )),
        @ApiResponse(responseCode = "404", description = "Vacuna no encontrada")
    })
    @GetMapping("/{id}")
    public Vacuna obtenerPorId(
        @Parameter(description = "ID de la vacuna", required = true) @PathVariable String id) {
        return vacunaService.findById(id);
    }

    @Operation(summary = "Guardar una nueva vacuna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vacuna guardada correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vacuna.class),
                examples = @ExampleObject(name = "EjemploVacunaGuardada", value = "{\"idVacuna\":\"VAC002\",\"nombre\":\"Parvovirus\",\"descripcion\":\"Previene parvovirus canino\",\"obligatoria\":false}")
            ))
    })
    @PostMapping
    public Vacuna guardar(
        @RequestBody(description = "Vacuna a crear", required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vacuna.class),
                examples = @ExampleObject(name = "EjemploVacunaNueva", value = "{\"idVacuna\":\"VAC003\",\"nombre\":\"Moquillo\",\"descripcion\":\"Vacuna contra moquillo canino\",\"obligatoria\":true}")
            )) @org.springframework.web.bind.annotation.RequestBody Vacuna vacuna) {
        return vacunaService.save(vacuna);
    }

    @Operation(summary = "Actualizar una vacuna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vacuna actualizada correctamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vacuna.class))),
        @ApiResponse(responseCode = "404", description = "Vacuna no encontrada")
    })
    @PutMapping
    public Vacuna actualizar(
        @RequestBody(description = "Vacuna con datos actualizados", required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Vacuna.class)
            )) @org.springframework.web.bind.annotation.RequestBody Vacuna vacuna) {
        return vacunaService.update(vacuna);
    }

    @Operation(summary = "Eliminar una vacuna por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vacuna eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vacuna no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminar(
        @Parameter(description = "ID de la vacuna", required = true) @PathVariable String id) {
        vacunaService.delete(id);
    }
}
