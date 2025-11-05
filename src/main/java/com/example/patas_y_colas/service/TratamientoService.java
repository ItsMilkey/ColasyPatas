package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Tratamiento;
import com.example.patas_y_colas.repository.TratamientoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    public List<Tratamiento> findAll() {
        return tratamientoRepository.findAll();
    }

    public Tratamiento findById(int idTratamiento) {
        return tratamientoRepository.findById(idTratamiento)
                .orElseThrow(() -> new EntityNotFoundException("Tratamiento no encontrado con ID: " + idTratamiento));
    }

    public Tratamiento save(Tratamiento tratamiento) {
        if (tratamiento == null) {
            throw new IllegalArgumentException("El tratamiento no puede ser nulo");
        }
        if (tratamiento.getDiagnostico() == null || tratamiento.getDiagnostico().trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico es requerido");
        }
        return tratamientoRepository.save(tratamiento);
    }

    public Tratamiento update(Tratamiento tratamiento) {
        if (tratamiento == null || tratamiento.getIdTratamiento() == 0) {
            throw new IllegalArgumentException("Datos del tratamiento inválidos");
        }
        if (!tratamientoRepository.existsById(tratamiento.getIdTratamiento())) {
            throw new EntityNotFoundException("Tratamiento no encontrado con ID: " + tratamiento.getIdTratamiento());
        }
        return tratamientoRepository.save(tratamiento);
    }

    public void delete(int idTratamiento) {
        if (!tratamientoRepository.existsById(idTratamiento)) {
            throw new EntityNotFoundException("No se puede eliminar. Tratamiento no encontrado con ID: " + idTratamiento);
        }
        tratamientoRepository.deleteById(idTratamiento);
    }

    public List<Tratamiento> findByPerroChipId(String chipId) {
        return tratamientoRepository.findByPerroChipId(chipId);
    }

    public List<Tratamiento> findByDiagnostico(String diagnostico) {
        return tratamientoRepository.findByDiagnosticoIgnoreCase(diagnostico);
    }

    //public List<Tratamiento> obtenerTratamientosActivos() {
    //    LocalDate hoy = LocalDate.now();
    //    return tratamientoRepository.findByFechaInicioLessThanEqualAndFechaTerminoGreaterThanEqual(hoy, hoy);
    //}

    public Tratamiento actualizarDiagnostico(int idTratamiento, String nuevoDiagnostico) {
        if (nuevoDiagnostico == null || nuevoDiagnostico.trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico no puede estar vacío");
        }

        Tratamiento tratamiento = findById(idTratamiento);
        tratamiento.actualizarDiagnostico(nuevoDiagnostico);
        return tratamientoRepository.save(tratamiento);
    }

    public int obtenerDuracionTratamiento(int idTratamiento) {
        Tratamiento tratamiento = findById(idTratamiento);
        return tratamiento.duracionDias();
    }
}
