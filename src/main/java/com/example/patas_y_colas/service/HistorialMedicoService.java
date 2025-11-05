package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.HistorialMedico;
import com.example.patas_y_colas.repository.HistorialMedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    public List<HistorialMedico> findAll() {
        return historialMedicoRepository.findAll();
    }

    public HistorialMedico findById(int idHistorial) {
        return historialMedicoRepository.findById(idHistorial)
                .orElseThrow(() -> new EntityNotFoundException("Historial médico no encontrado con ID: " + idHistorial));
    }

    public HistorialMedico save(HistorialMedico historialMedico) {
        return historialMedicoRepository.save(historialMedico);
    }

    public void delete(int idHistorial) {
        if (!historialMedicoRepository.existsById(idHistorial)) {
            throw new EntityNotFoundException("No se puede eliminar. Historial médico no encontrado con ID: " + idHistorial);
        }
        historialMedicoRepository.deleteById(idHistorial);
    }

    // Buscar historial médico de un perro por su chipId
    public HistorialMedico findByPerroChipId(String chipId) {
        return historialMedicoRepository.findByPerroChipId(chipId);
    }

    // Calcular promedio de pesos dentro de un historial específico
    public float calcularPromedioPeso(int idHistorial) {
        HistorialMedico historial = findById(idHistorial);
        return historial.calcularPromedioPeso();
    }

    // Obtener último diagnóstico registrado
    public String obtenerUltimoDiagnostico(int idHistorial) {
        HistorialMedico historial = findById(idHistorial);
        return historial.ultimoDiagnostico();
    }

    // Listar consultas de todos los historiales
    public List<String> listarConsultas() {
        return historialMedicoRepository.findAll().stream()
                .flatMap(h -> h.listarConsultas().stream())
                .toList();
    }

    // Listar diagnósticos de todos los historiales
    public List<String> listarDiagnosticos() {
        return historialMedicoRepository.findAll().stream()
                .flatMap(h -> h.listarDiagnosticos().stream())
                .toList();
    }
}
