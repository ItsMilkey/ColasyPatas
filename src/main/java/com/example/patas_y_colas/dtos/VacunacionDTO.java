package com.example.patas_y_colas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO utilizado para la creación o modificación de registros de vacunación")
public class VacunacionDTO {

    @Schema(description = "ID único de la vacunación", example = "VAC123")
    private String idVacunacion;

    @Schema(description = "Fecha en la que se aplicó la vacuna", example = "2025-05-01")
    private LocalDate fechaAplicacion;

    @Schema(description = "Fecha programada para la próxima dosis", example = "2025-11-01")
    private LocalDate fechaProxima;
}
