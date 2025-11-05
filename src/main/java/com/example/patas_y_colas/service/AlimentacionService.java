package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Alimentacion;
import com.example.patas_y_colas.repository.AlimentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentacionService {

    @Autowired
    private AlimentacionRepository alimentacionRepository;

    
    public List<Alimentacion> findAll() {
        return alimentacionRepository.findAll();
    }

    public Alimentacion findById(int idAlimentacion){
        return alimentacionRepository.findById(idAlimentacion).get();
    }

    public Alimentacion save(Alimentacion alimentacion){
        return alimentacionRepository.save(alimentacion);
    }

    public void delete(int idAlimentacion){
        alimentacionRepository.deleteById(idAlimentacion);
    }

}