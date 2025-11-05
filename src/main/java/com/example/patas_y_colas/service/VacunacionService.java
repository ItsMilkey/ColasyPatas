package com.example.patas_y_colas.service;

import com.example.patas_y_colas.dtos.VacunacionDTO;
import com.example.patas_y_colas.model.Vacunacion;
import com.example.patas_y_colas.repository.VacunacionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VacunacionService {

    @Autowired
    private VacunacionRepository vacunacionRepository;

    public List<Vacunacion> findAll() {
        return vacunacionRepository.findAll();
    }

    public Vacunacion findById(String idVacunacion) {
        return vacunacionRepository.findById(idVacunacion)
                .orElseThrow(() -> new EntityNotFoundException("Vacunación no encontrada con ID: " + idVacunacion));
    }

    public Vacunacion save(VacunacionDTO dto) {
        if (dto == null || dto.getIdVacunacion() == null || dto.getIdVacunacion().trim().isEmpty()) {
            throw new IllegalArgumentException("Los datos de vacunación son inválidos");
        }
        Vacunacion vac = new Vacunacion();
        vac.setIdVacunacion(dto.getIdVacunacion());
        vac.setFechaAplicacion(dto.getFechaAplicacion());
        vac.setFechaProxima(dto.getFechaProxima());

        return vacunacionRepository.save(vac);
    }

    public void delete(String idVacunacion) {
        if (!vacunacionRepository.existsById(idVacunacion)) {
            throw new EntityNotFoundException("No se puede eliminar. Vacunación no encontrada con ID: " + idVacunacion);
        }
        vacunacionRepository.deleteById(idVacunacion);
    }

    public boolean estaVacunaVencida(String idVacunacion) {
        Vacunacion vac = findById(idVacunacion);
        return vac.estaVencida();
    }

    public int diasHastaProximaVacuna(String idVacunacion) {
        Vacunacion vac = findById(idVacunacion);
        return vac.diasParaProxima();
    }

    public List<Vacunacion> findByPerroChipId(String chipId) {
        return vacunacionRepository.findByPerroChipId(chipId);
    }

    public List<Vacunacion> findVencidasByPerro(String chipId) {
        return vacunacionRepository.findByPerroChipIdAndFechaProximaBefore(chipId, java.time.LocalDate.now());
    }

    public List<Vacunacion> findVigentesByPerro(String chipId) {
        return vacunacionRepository.findByPerroChipIdAndFechaProximaGreaterThanEqual(chipId, java.time.LocalDate.now());
    }
}
