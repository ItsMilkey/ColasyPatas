package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Peso;
import com.example.patas_y_colas.repository.PesoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PesoService {

    @Autowired
    private PesoRepository pesoRepository;

    public List<Peso> findAll() {
        return pesoRepository.findAll();
    }

    public Peso findById(int idPeso) {
        return pesoRepository.findById(idPeso)
                .orElseThrow(() -> new EntityNotFoundException("Peso no encontrado con ID: " + idPeso));
    }

    public Peso save(Peso peso) {
        return pesoRepository.save(peso);
    }

    public Peso update(Peso peso) {
        if (peso == null || peso.getIdPeso() == 0) {
            throw new IllegalArgumentException("Datos del peso inválidos");
        }
        if (!pesoRepository.existsById(peso.getIdPeso())) {
            throw new EntityNotFoundException("Peso no encontrado con ID: " + peso.getIdPeso());
        }
        return pesoRepository.save(peso);
    }

    public void delete(int idPeso) {
        if (!pesoRepository.existsById(idPeso)) {
            throw new EntityNotFoundException("No se puede eliminar. Peso no encontrado con ID: " + idPeso);
        }
        pesoRepository.deleteById(idPeso);
    }

    public List<Peso> findByPerroChipId(String chipId) {
        return pesoRepository.findByPerroChipId(chipId);
    }

    public List<Peso> findByPerroChipIdOrderByFechaDesc(String chipId) {
        return pesoRepository.findByPerroChipIdOrderByFechaDesc(chipId);
    }

    public List<Peso> findByFecha(Date fecha) {
        return pesoRepository.findByFecha(fecha);
    }

    public boolean esPesoNormal(int idPeso, double rangoMin, double rangoMax) {
        Peso peso = findById(idPeso);
        double valorPeso = peso.getPesoKg();
        return valorPeso >= rangoMin && valorPeso <= rangoMax;
    }
}
