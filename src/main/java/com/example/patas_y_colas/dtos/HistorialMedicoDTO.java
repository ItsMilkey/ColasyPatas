package com.example.patas_y_colas.dtos;

import java.util.List;
import lombok.Data;

@Data
public class HistorialMedicoDTO {
    private int idHistorial;
    private String consultas;
    private List<String> diagnosticos;
}


