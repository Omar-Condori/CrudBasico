package omar.example.omarweb.controller;

import omar.example.omarweb.entity.Producto;
import omar.example.omarweb.repository.ProductoRepository;
import omar.example.omarweb.repository.CategoriaRepository;
import omar.example.omarweb.entity.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/activos")
    public List<Producto> getProductosActivos() {
        return productoRepository.findByActivoTrue();
    }

    @GetMapping("/disponibles")
    public List<Producto> getProductosDisponibles() {
        return productoRepository.findProductosDisponibles();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> getProductosPorCategoria(@PathVariable Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @GetMapping("/buscar")
    public List<Producto> buscarProductosPorNombre(@RequestParam String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @GetMapping("/precio")
    public List<Producto> getProductosPorRangoPrecio(
            @RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    @GetMapping("/precio-maximo")
    public List<Producto> getProductosPorPrecioMaximo(@RequestParam BigDecimal precioMax) {
        return productoRepository.findProductosPorPrecioMaximo(precioMax);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        // Verificar si la categoría existe
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                    .orElse(null);
            if (categoria == null) {
                return ResponseEntity.badRequest().build();
            }
            producto.setCategoria(categoria);
        }
        
        Producto productoGuardado = productoRepository.save(producto);
        return ResponseEntity.ok().body(productoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStockDisponible(productoActualizado.getStockDisponible());
                    producto.setActivo(productoActualizado.isActivo());
                    
                    // Actualizar categoría si se proporciona
                    if (productoActualizado.getCategoria() != null && productoActualizado.getCategoria().getId() != null) {
                        Categoria categoria = categoriaRepository.findById(productoActualizado.getCategoria().getId())
                                .orElse(null);
                        if (categoria != null) {
                            producto.setCategoria(categoria);
                        }
                    }
                    
                    Producto productoGuardado = productoRepository.save(producto);
                    return ResponseEntity.ok().body(productoGuardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    productoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 