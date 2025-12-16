package com.example.patas_y_colas.service;

import com.example.patas_y_colas.dtos.CompraDTO;
import com.example.patas_y_colas.model.*;
import com.example.patas_y_colas.repository.ProductoRepository;
import com.example.patas_y_colas.repository.UsuarioRepository;
import com.example.patas_y_colas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Venta realizarCompra(String emailUsuario, CompraDTO compraDTO) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setFecha(LocalDateTime.now());
        
        List<DetalleVenta> detalles = new ArrayList<>();
        double totalVenta = 0;

        for (CompraDTO.ItemCompraDTO item : compraDTO.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + item.getProductoId()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrice());

            totalVenta += (producto.getPrice() * item.getCantidad());
            detalles.add(detalle);
        }

        venta.setTotal(totalVenta);
        venta.setDetalles(detalles);

        return ventaRepository.save(venta);
    }

    public List<Venta> obtenerHistorial(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ventaRepository.findByUsuarioIdOrderByFechaDesc(usuario.getId());
    }
}