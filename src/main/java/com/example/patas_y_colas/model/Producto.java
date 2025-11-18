package com.example.patas_y_colas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private double price;

    @Column(name = "IMAGE", nullable = true, length = 1000)
    private String image;

    // --- NUEVO CAMPO: CATEGORÍA ---
    @Column(name = "CATEGORY", nullable = false, length = 100)
    private String category;
}