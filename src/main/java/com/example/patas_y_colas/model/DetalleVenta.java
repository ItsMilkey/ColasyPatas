package com.example.patas_y_colas.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // 1. IMPORTACIÓN IMPORTANTE
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DETALLE_VENTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VENTA_ID", nullable = false)
    @JsonIgnore // 2. ESTA ANOTACIÓN EVITA EL BUCLE INFINITO
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "PRODUCTO_ID", nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "PRECIO_UNITARIO", nullable = false)
    private double precioUnitario; // Guardamos el precio al momento de la compra
}