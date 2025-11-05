package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.TipoComida;
import com.example.patas_y_colas.repository.TipoComidaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TipoComidaService {

    @Autowired
    private TipoComidaRepository tipoComidaRepository;

    public List<TipoComida> findAll() {
        return tipoComidaRepository.findAll();
    }

    public TipoComida findById(int idTipo) {
        return tipoComidaRepository.findById(idTipo)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de comida no encontrado con ID: " + idTipo));
    }

    public TipoComida save(TipoComida tipoComida) {
        return tipoComidaRepository.save(tipoComida);
    }

    public TipoComida update(TipoComida tipoComida) {
        if (tipoComida == null || tipoComida.getIdTipo() == 0) {
            throw new IllegalArgumentException("Datos del tipo de comida inválidos");
        }
        if (!tipoComidaRepository.existsById(tipoComida.getIdTipo())) {
            throw new EntityNotFoundException("Tipo de comida no encontrado con ID: " + tipoComida.getIdTipo());
        }
        return tipoComidaRepository.save(tipoComida);
    }

    public void delete(int idTipo) {
        if (!tipoComidaRepository.existsById(idTipo)) {
            throw new EntityNotFoundException("No se puede eliminar. Tipo de comida no encontrado con ID: " + idTipo);
        }
        tipoComidaRepository.deleteById(idTipo);
    }

    public List<TipoComida> findByPerroChipId(String chipId) {
        return tipoComidaRepository.findByPerroChipId(chipId);
    }

    public List<TipoComida> findByNombre(String nombre) {
        return tipoComidaRepository.findByNombreIgnoreCase(nombre);
    }
}
