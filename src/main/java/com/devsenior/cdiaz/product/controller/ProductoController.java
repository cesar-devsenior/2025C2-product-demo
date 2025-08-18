package com.devsenior.cdiaz.product.controller;

import com.devsenior.cdiaz.product.entity.Producto;
import com.devsenior.cdiaz.product.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de productos
 * Proporciona endpoints para operaciones CRUD
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    private final ProductoService productoService;
    
    /**
     * Constructor con inyección de dependencias
     * @param productoService Servicio de productos
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    /**
     * GET - Obtiene todos los productos
     * @return Lista de todos los productos
     */
    @GetMapping  // GET http://locahost:8080/api/productos
    public ResponseEntity<List<Producto>> getAllProductos() {
        try {
            List<Producto> productos = productoService.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET - Obtiene un producto por ID
     * @param id ID del producto
     * @return Producto encontrado o 404 si no existe
     */
    @GetMapping("/{id}") // GET http://locahost:8080/api/productos/{id}
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        try {
            Optional<Producto> producto = productoService.findById(id);
            return producto.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST - Crea un nuevo producto
     * @param producto Producto a crear
     * @return Producto creado con ID asignado
     */
    @PostMapping // POST http://locahost:8080/api/productos
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        try {
            Producto productoGuardado = productoService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * PUT - Actualiza un producto existente
     * @param id ID del producto a actualizar
     * @param producto Producto con los datos actualizados
     * @return Producto actualizado o 404 si no existe
     */
    @PutMapping("/{id}") // PUT http://locahost:8080/api/productos/{id}
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            // Verificar si el producto existe
            if (!productoService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            // Asignar el ID del path al producto
            producto.setId(id);
            Producto productoActualizado = productoService.save(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DELETE - Elimina un producto por ID
     * @param id ID del producto a eliminar
     * @return 204 si se eliminó correctamente, 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        try {
            boolean eliminado = productoService.deleteById(id);
            if (eliminado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET - Busca productos por nombre (búsqueda parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de productos que coincidan con la búsqueda
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            List<Producto> productos = productoService.findByNombreContaining(nombre);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET - Busca productos por rango de precios
     * @param precioMinimo Precio mínimo del rango
     * @param precioMaximo Precio máximo del rango
     * @return Lista de productos dentro del rango de precios
     */
    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> buscarPorRangoPrecio(
            @RequestParam Double precioMinimo,
            @RequestParam Double precioMaximo) {
        try {
            List<Producto> productos = productoService.findByPrecioBetween(precioMinimo, precioMaximo);
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET - Cuenta el total de productos
     * @return Número total de productos
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getProductoCount() {
        try {
            long count = productoService.count();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET - Verifica si existe un producto con el ID especificado
     * @param id ID del producto a verificar
     * @return true si existe, false en caso contrario
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsProducto(@PathVariable Long id) {
        try {
            boolean exists = productoService.existsById(id);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
