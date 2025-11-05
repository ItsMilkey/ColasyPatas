package com.example.patas_y_colas.dtos;

import lombok.Data;

@Data
public class VacunaDTO {
    private int idVacuna;
    private String nombre;
    private String descripcion;
    private boolean obligatoria;
}