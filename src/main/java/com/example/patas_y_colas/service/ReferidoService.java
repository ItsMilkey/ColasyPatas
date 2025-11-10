package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Referido;
import com.example.patas_y_colas.repository.ReferidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
@Transactional
public class ReferidoService {

    @Autowired
    private ReferidoRepository referidoRepository;

    /**
     * Devuelve todos los códigos de referido.
     */
    public List<Referido> getAllReferidos() {
        return referidoRepository.findAll();
    }

    /**
     * Guarda un nuevo código de referido.
     */
    public Referido saveReferido(Referido referido) {
        // En un futuro, aquí se podría generar el código automáticamente
        // si no se proveyó uno.
        return referidoRepository.save(referido);
    }

    /**
     * Elimina un código de referido por su ID.
     */
    public void deleteReferido(Long id) {
        if (!referidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Código de referido no encontrado con ID: " + id);
        }
        referidoRepository.deleteById(id);
    }
}