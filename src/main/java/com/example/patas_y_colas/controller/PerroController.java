package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.*;
import com.example.patas_y_colas.service.PerroService;
import com.example.patas_y_colas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perros")
@Tag(name = "Perros", description = "Operaciones relacionadas con la gestión de perros")
public class PerroController {

    @Autowired
    private PerroService perroService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los perros")
    @GetMapping
    public ResponseEntity<List<Perro>> getAllPerros() {
        return ResponseEntity.ok(perroService.findAll());
    }

    @Operation(summary = "Obtener un perro por su chip ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perro encontrado"),
        @ApiResponse(responseCode = "404", description = "Perro no encontrado")
    })
    @GetMapping("/{chipId}")
    public ResponseEntity<Perro> getPerroByChip(
        @Parameter(description = "ID del chip del perro") @PathVariable String chipId) {
        try {
            Perro perro = perroService.findByChipId(chipId);
            return ResponseEntity.ok(perro);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo perro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perro creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Perro> createPerro(@RequestBody Perro perro) {
        if (perro.getUsuario() == null || perro.getUsuario().getRut() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Usuario usuario = usuarioService.findByRut(perro.getUsuario().getRut());
        if (usuario == null) {
            return ResponseEntity.badRequest().body(null);
        }
        perro.setUsuario(usuario);
        try {
            Perro saved = perroService.save(perro);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Actualizar información de un perro")
    @PutMapping("/{chipId}")
    public ResponseEntity<Perro> updatePerro(
        @Parameter(description = "ID del chip del perro") @PathVariable String chipId,
        @RequestBody Perro perro) {
        if (!chipId.equals(perro.getChipId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Perro updated = perroService.update(perro);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un perro por chip ID")
    @DeleteMapping("/{chipId}")
    public ResponseEntity<Void> deletePerro(
        @Parameter(description = "ID del chip del perro") @PathVariable String chipId) {
        try {
            perroService.delete(chipId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener perros por RUT del usuario")
    @GetMapping("/usuario/{rut}")
    public ResponseEntity<List<Perro>> getPerrosByUsuario(
        @Parameter(description = "RUT del usuario") @PathVariable String rut) {
        Usuario usuario = usuarioService.findByRut(rut);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(perroService.findByUsuario(usuario));
    }

    // Métodos para agregar colecciones

    @Operation(summary = "Agregar vacunas al perro")
    @PostMapping("/{chipId}/vacunas")
    public ResponseEntity<Perro> agregarVacunas(
        @PathVariable String chipId, @RequestBody List<Vacuna> vacunas) {
        return agregarColeccion(chipId, vacunas, Perro::getVacunas, Perro::setVacunas);
    }

    @Operation(summary = "Agregar registros de vacunación")
    @PostMapping("/{chipId}/vacunaciones")
    public ResponseEntity<Perro> agregarVacunaciones(
        @PathVariable String chipId, @RequestBody List<Vacunacion> vacunaciones) {
        return agregarColeccion(chipId, vacunaciones, Perro::getVacunaciones, Perro::setVacunaciones);
    }

    @Operation(summary = "Agregar dietas")
    @PostMapping("/{chipId}/dietas")
    public ResponseEntity<Perro> agregarDietas(
        @PathVariable String chipId, @RequestBody List<Dieta> dietas) {
        return agregarColeccion(chipId, dietas, Perro::getDietas, Perro::setDietas);
    }

    @Operation(summary = "Agregar tipos de comida")
    @PostMapping("/{chipId}/tipos-comida")
    public ResponseEntity<Perro> agregarTiposComida(
        @PathVariable String chipId, @RequestBody List<TipoComida> tiposComida) {
        return agregarColeccion(chipId, tiposComida, Perro::getTiposComida, Perro::setTiposComida);
    }

    @Operation(summary = "Agregar registros de peso")
    @PostMapping("/{chipId}/pesos")
    public ResponseEntity<Perro> agregarPesos(
        @PathVariable String chipId, @RequestBody List<Peso> pesos) {
        return agregarColeccion(chipId, pesos, Perro::getPesos, Perro::setPesos);
    }

    @Operation(summary = "Agregar registros de alimentación")
    @PostMapping("/{chipId}/alimentaciones")
    public ResponseEntity<Perro> agregarAlimentaciones(
        @PathVariable String chipId, @RequestBody List<Alimentacion> alimentaciones) {
        return agregarColeccion(chipId, alimentaciones, Perro::getAlimentaciones, Perro::setAlimentaciones);
    }

    @Operation(summary = "Agregar tratamientos")
    @PostMapping("/{chipId}/tratamientos")
    public ResponseEntity<Perro> agregarTratamientos(
        @PathVariable String chipId, @RequestBody List<Tratamiento> tratamientos) {
        return agregarColeccion(chipId, tratamientos, Perro::getTratamientos, Perro::setTratamientos);
    }

    @Operation(summary = "Agregar historial médico")
    @PostMapping("/{chipId}/historial")
    public ResponseEntity<Perro> agregarHistorial(
        @PathVariable String chipId, @RequestBody HistorialMedico historial) {
        try {
            Perro perro = perroService.findByChipId(chipId);
            perro.setHistorialMedico(historial);
            Perro actualizado = perroService.update(perro);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener edad del perro")
    @GetMapping("/{chipId}/edad")
    public ResponseEntity<Integer> getEdadPerro(
        @PathVariable String chipId) {
        try {
            Perro perro = perroService.findByChipId(chipId);
            return ResponseEntity.ok(perro.getEdad());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Método genérico para agregar listas de entidades relacionadas
    private <T> ResponseEntity<Perro> agregarColeccion(
        String chipId, List<T> nuevosElementos,
        java.util.function.Function<Perro, List<T>> getter,
        java.util.function.BiConsumer<Perro, List<T>> setter) {
        try {
            Perro perro = perroService.findByChipId(chipId);
            List<T> elementos = getter.apply(perro);
            if (elementos == null) {
                elementos = new ArrayList<>();
            }
            elementos.addAll(nuevosElementos);
            setter.accept(perro, elementos);
            Perro actualizado = perroService.update(perro);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
