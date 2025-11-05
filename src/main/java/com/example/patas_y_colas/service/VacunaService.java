package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Vacuna;
import com.example.patas_y_colas.repository.VacunaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VacunaService {

    @Autowired
    private VacunaRepository vacunaRepository;

    public List<Vacuna> findAll() {
        return vacunaRepository.findAll();
    }

    public Vacuna findById(String idVacuna) {
        return vacunaRepository.findById(idVacuna)
                .orElseThrow(() -> new EntityNotFoundException("Vacuna no encontrada con ID: " + idVacuna));
    }

    public Vacuna save(Vacuna vacuna) {
        if (vacuna == null || vacuna.getIdVacuna() == null || vacuna.getIdVacuna().trim().isEmpty()) {
            throw new IllegalArgumentException("Los datos de la vacuna son inválidos");
        }
        return vacunaRepository.save(vacuna);
    }

    public Vacuna update(Vacuna vacuna) {
        if (vacuna == null || vacuna.getIdVacuna() == null || vacuna.getIdVacuna().trim().isEmpty()) {
            throw new IllegalArgumentException("Datos de la vacuna inválidos");
        }
        if (!vacunaRepository.existsById(vacuna.getIdVacuna())) {
            throw new EntityNotFoundException("Vacuna no encontrada con ID: " + vacuna.getIdVacuna());
        }
        return vacunaRepository.save(vacuna);
    }

    public void delete(String idVacuna) {
        if (!vacunaRepository.existsById(idVacuna)) {
            throw new EntityNotFoundException("No se puede eliminar. Vacuna no encontrada con ID: " + idVacuna);
        }
        vacunaRepository.deleteById(idVacuna);
    }

    public List<Vacuna> findByNombre(String nombre) {
        return vacunaRepository.findByNombreIgnoreCase(nombre);
    }

    public List<Vacuna> findObligatorias() {
        return vacunaRepository.findByObligatoria(true);
    }

    public List<Vacuna> findOpcionales() {
        return vacunaRepository.findByObligatoria(false);
    }
}
