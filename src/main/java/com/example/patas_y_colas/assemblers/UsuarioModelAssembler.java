package com.example.patas_y_colas.assemblers;

import com.example.patas_y_colas.controller.UsuarioController;
import com.example.patas_y_colas.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioController.class).buscar(usuario.getRut())).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
    }
}





    
