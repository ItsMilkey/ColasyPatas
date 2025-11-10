package com.example.patas_y_colas.service;

import com.example.patas_y_colas.model.Producto;
import com.example.patas_y_colas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Devuelve todos los productos.
     */
    public List<Producto> getAllProducts() {
        return productoRepository.findAll();
    }

    /**
     * Guarda un nuevo producto o actualiza uno existente.
     */
    public Producto saveProduct(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto por su ID.
     */
    public void deleteProduct(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
}