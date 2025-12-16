package com.example.patas_y_colas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "VENTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "TOTAL", nullable = false)
    private double total;

    // --- CAMBIO CLAVE AQUÍ ---
    // Cambiamos a EAGER para que SIEMPRE traiga la lista de productos al consultar la venta
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleVenta> detalles;
}