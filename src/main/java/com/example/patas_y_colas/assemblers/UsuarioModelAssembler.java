package com.example.patas_y_colas.assemblers;

import com.example.patas_y_colas.controller.UsuarioController; 
import com.example.patas_y_colas.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
// import org.springframework.stereotype.Component; // <-- LO COMENTAMOS

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// @Component // <-- APAGADO TEMPORALMENTE
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        // Este código ahora está inactivo y no dará error de compilación
        return EntityModel.of(usuario);
        
        // return EntityModel.of(usuario,
        //         linkTo(methodOn(UsuarioController.class).buscar(usuario.getId())).withSelfRel(),
        //         linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
    }
}