package com.example.patas_y_colas.controller;

import com.example.patas_y_colas.model.Producto;
import com.example.patas_y_colas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products") // La ruta que definimos en el frontend
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Endpoint para LISTAR todos los productos (GET /api/products)
     * Devuelve una lista vacía [] si no hay productos, lo cual es
     * correcto para que el frontend no falle.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProducts() {
        List<Producto> productos = productoService.getAllProducts();
        return ResponseEntity.ok(productos);
    }

    /**
     * Endpoint para AGREGAR un producto (POST /api/products)
     */
    @PostMapping
    public ResponseEntity<Producto> createProduct(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.saveProduct(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    /**
     * Endpoint para ELIMINAR un producto (DELETE /api/products/{id})
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productoService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // 204 OK
        } catch (Exception e) {
            // Esto atrapará el EntityNotFoundException si no existe
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
//