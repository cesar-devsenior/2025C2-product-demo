package com.devsenior.cdiaz.product.service;

import com.devsenior.cdiaz.product.entity.Producto;
import com.devsenior.cdiaz.product.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos
 * Proporciona métodos para operaciones CRUD y lógica de negocio
 */
@Service
@Transactional
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    
    /**
     * Constructor con inyección de dependencias
     * @param productoRepository Repositorio de productos
     */
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    /**
     * Obtiene todos los productos
     * @return Lista de todos los productos
     */
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    
    /**
     * Busca un producto por su ID
     * @param id ID del producto a buscar
     * @return Optional que puede contener el producto encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return productoRepository.findById(id);
    }
    
    /**
     * Guarda un producto (crear o actualizar)
     * @param producto Producto a guardar
     * @return Producto guardado con ID asignado
     */
    public Producto save(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        
        // Validaciones básicas
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        
        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor o igual a 0");
        }
        
        return productoRepository.save(producto);
    }
    
    /**
     * Elimina un producto por su ID
     * @param id ID del producto a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Verifica si existe un producto con el ID especificado
     * @param id ID del producto a verificar
     * @return true si existe, false en caso contrario
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return productoRepository.existsById(id);
    }
    
    /**
     * Cuenta el total de productos
     * @return Número total de productos
     */
    @Transactional(readOnly = true)
    public long count() {
        return productoRepository.count();
    }
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de productos que coincidan con la búsqueda
     */
    @Transactional(readOnly = true)
    public List<Producto> findByNombreContaining(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of();
        }
        return productoRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }
    
    /**
     * Busca productos por rango de precios
     * @param precioMinimo Precio mínimo del rango
     * @param precioMaximo Precio máximo del rango
     * @return Lista de productos dentro del rango de precios
     */
    @Transactional(readOnly = true)
    public List<Producto> findByPrecioBetween(Double precioMinimo, Double precioMaximo) {
        if (precioMinimo == null || precioMaximo == null) {
            throw new IllegalArgumentException("Los precios no pueden ser nulos");
        }
        
        if (precioMinimo < 0 || precioMaximo < 0) {
            throw new IllegalArgumentException("Los precios deben ser mayores o iguales a 0");
        }
        
        if (precioMinimo > precioMaximo) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor al precio máximo");
        }
        
        return productoRepository.findByPrecioBetween(precioMinimo, precioMaximo);
    }
}
