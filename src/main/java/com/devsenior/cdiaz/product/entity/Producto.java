package com.devsenior.cdiaz.product.entity;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un producto
 */
@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    
    @Column(name = "precio", nullable = false)
    private Double precio;
    
    @Column(name = "imagen_url", nullable = true, length = 500)
    private String imagenUrl;
    
    // Constructor por defecto
    public Producto() {
    }
    
    // Constructor con parámetros
    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
    
    // Constructor con todos los parámetros
    public Producto(String nombre, Double precio, String imagenUrl) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Double getPrecio() {
        return precio;
    }
    
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    public String getImagenUrl() {
        return imagenUrl;
    }
    
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", imagenUrl='" + imagenUrl + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Producto producto = (Producto) o;
        
        return id != null ? id.equals(producto.id) : producto.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
