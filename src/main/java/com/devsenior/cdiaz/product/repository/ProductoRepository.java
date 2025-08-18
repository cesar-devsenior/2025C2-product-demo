package com.devsenior.cdiaz.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsenior.cdiaz.product.entity.Producto;

/**
 * Repositorio JPA para la entidad Producto
 * Proporciona métodos CRUD básicos y consultas personalizadas
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Busca productos por nombre (búsqueda parcial, case-insensitive)
     * @param nombre Nombre parcial del producto a buscar
     * @return Lista de productos que contengan el nombre especificado
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca productos por rango de precios
     * @param precioMinimo Precio mínimo del rango
     * @param precioMaximo Precio máximo del rango
     * @return Lista de productos dentro del rango de precios
     */
    List<Producto> findByPrecioBetween(Double precioMinimo, Double precioMaximo);
}
