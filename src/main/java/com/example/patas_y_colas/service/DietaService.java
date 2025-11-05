package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Dieta;
import com.example.patas_y_colas.repository.DietaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DietaService {

    @Autowired
    private DietaRepository dietaRepository;

    public List<Dieta> findAll() {
        return dietaRepository.findAll();
    }

    public Dieta findById(int idDieta) {
        return dietaRepository.findById(idDieta)
                .orElseThrow(() -> new EntityNotFoundException("Dieta no encontrada con ID: " + idDieta));
    }

    // Save crea o actualiza según corresponda
    public Dieta save(Dieta dieta) {
        return dietaRepository.save(dieta);
    }

    public void delete(int idDieta) {
        if (!dietaRepository.existsById(idDieta)) {
            throw new EntityNotFoundException("No se puede eliminar. Dieta no encontrada con ID: " + idDieta);
        }
        dietaRepository.deleteById(idDieta);
    }

    // Actualizar solo descripción de una dieta
    public Dieta actualizarDescripcion(int idDieta, String nuevaDescripcion) {
        Dieta dieta = dietaRepository.findById(idDieta)
                .orElseThrow(() -> new EntityNotFoundException("Dieta no encontrada con ID: " + idDieta));
        dieta.actualizarDescripcion(nuevaDescripcion);
        return dietaRepository.save(dieta);
    }

    // Ejemplo: buscar dietas por chipId de perro
    public List<Dieta> findByPerro(String chipId) {
        return dietaRepository.findByPerroChipId(chipId);
    }

    // Ejemplo: buscar dietas activas de un perro (opcional)
    public List<Dieta> findDietasVigentesPorPerro(String chipId) {
        var hoy = java.time.LocalDate.now();
        return dietaRepository.findByPerroChipIdAndFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(
                chipId, hoy, hoy
        );
    }
}
