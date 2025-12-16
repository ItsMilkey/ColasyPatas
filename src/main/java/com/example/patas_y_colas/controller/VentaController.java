package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.dtos.CompraDTO;
import com.example.patas_y_colas.model.Venta;
import com.example.patas_y_colas.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // Endpoint para COMPRAR (Recibe el carrito)
    @PostMapping("/comprar")
    public ResponseEntity<?> realizarCompra(@RequestBody CompraDTO compraDTO) {
        // Obtenemos el email del usuario logueado desde el Token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Venta venta = ventaService.realizarCompra(email, compraDTO);
        return ResponseEntity.ok("Compra realizada con éxito. ID Venta: " + venta.getId());
    }

    // Endpoint para ver HISTORIAL
    @GetMapping("/historial")
    public ResponseEntity<List<Venta>> verHistorial() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return ResponseEntity.ok(ventaService.obtenerHistorial(email));
    }
}