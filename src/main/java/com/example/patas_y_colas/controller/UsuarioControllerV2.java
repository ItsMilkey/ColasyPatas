package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.assemblers.UsuarioModelAssembler;
import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> listar() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuario> buscar(@PathVariable String rut) {
        Usuario usuario = usuarioService.findByRut(rut);
        return assembler.toModel(usuario);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> guardar(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioController.class).buscar(nuevo.getRut())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> actualizar(@PathVariable String rut, @RequestBody Usuario usuario) {
        usuario.setRut(rut);
        Usuario actualizado = usuarioService.save(usuario);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable String rut) {
        usuarioService.delete(rut);
        return ResponseEntity.noContent().build();
    }
}