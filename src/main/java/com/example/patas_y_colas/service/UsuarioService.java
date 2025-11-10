package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // <-- IMPORTACIÓN AÑADIDA

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los usuarios.
     * (Equivalente a tu método 'findAll')
     */
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    /**
     * Guarda un usuario nuevo o actualiza uno existente.
     * (Equivalente a tu método 'save')
     */
    public Usuario saveUser(Usuario usuario) {
        // En el futuro, aquí podrías agregar lógica para encriptar la contraseña
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     * (Modificado para usar Long id en lugar de String rut)
     */
    public void deleteUser(Long id) {
        if (!usuarioRepository.existsById(id)) {
            // Actualizamos el mensaje de error
            throw new EntityNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su ID.
     * (Requerido por UsuarioControllerV2)
     */
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    // --- MÉTODOS ELIMINADOS ---
    // findByRut, findByRol, buscarPorCorreo, y autenticar se han eliminado...
}