package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Perro;
import com.example.patas_y_colas.model.Usuario;
import com.example.patas_y_colas.repository.PerroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PerroService {

    @Autowired
    private PerroRepository perroRepository;

    public List<Perro> findAll() {
    List<Perro> perros = perroRepository.findAll();
    // ⚠️ Forzamos la carga del usuario para cada perro
    perros.forEach(perro -> {
        if (perro.getUsuario() != null) {
            perro.getUsuario().getNombre(); // accede a cualquier campo para inicializar el proxy
        }
    });
    return perros;
}


    public Perro findByChipId(String chipId) {
        return perroRepository.findById(chipId)
                .orElseThrow(() -> new EntityNotFoundException("Perro no encontrado con chip ID: " + chipId));
    }

    public Perro save(Perro perro) {
        if (perro == null) {
            throw new IllegalArgumentException("El perro no puede ser null");
        }
        if (perro.getNombre() == null || perro.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del perro es requerido");
        }
        return perroRepository.save(perro);
    }

    public Perro update(Perro perro) {
        if (perro == null || perro.getChipId() == null || perro.getChipId().trim().isEmpty()) {
            throw new IllegalArgumentException("Datos del perro inválidos");
        }
        if (!perroRepository.existsById(perro.getChipId())) {
            throw new EntityNotFoundException("Perro no encontrado con chip ID: " + perro.getChipId());
        }
        return perroRepository.save(perro);
    }

    public void delete(String chipId) {
        if (!perroRepository.existsById(chipId)) {
            throw new EntityNotFoundException("No se puede eliminar. Perro no encontrado con chip ID: " + chipId);
        }
        perroRepository.deleteById(chipId);
    }

    public List<Perro> findByUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        return usuario.getPerros();
    }

    public boolean existsPerro(String chipId) {
        return perroRepository.existsById(chipId);
    }

    public List<Perro> findByRaza(String raza) {
        return perroRepository.findByRazaIgnoreCase(raza);
    }
}
