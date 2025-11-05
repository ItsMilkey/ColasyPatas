package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findByRut(String rut) {
        return usuarioRepository.findById(rut).get();
    }

    //Save crea tanto como actualiza
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(String rut) {
        if (!usuarioRepository.existsById(rut)) {
            throw new EntityNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + rut);
        }
        usuarioRepository.deleteById(rut);
    }

    public List<Usuario> findByRol(String rol) {
        return usuarioRepository.findByRol(rol);
    }

    public Usuario buscarPorCorreo(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con correo: " + email));
    }

    // Ejemplo opcional: método para login (solo si no usas Spring Security)
    public boolean autenticar(String email, String contrasena) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> usuario.getContrasena().equals(contrasena))
                .orElse(false);
    }

    

}