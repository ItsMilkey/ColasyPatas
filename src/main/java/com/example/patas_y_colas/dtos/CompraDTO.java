package com.example.patas_y_colas.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CompraDTO {
    // Esto es lo que envías desde React: una lista de items
    private List<ItemCompraDTO> items;

    @Data
    public static class ItemCompraDTO {
        private Long productoId;
        private int cantidad;
    }
}