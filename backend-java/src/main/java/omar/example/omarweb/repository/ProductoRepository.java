package omar.example.omarweb.repository;

import omar.example.omarweb.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Métodos personalizados
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT p FROM Producto p WHERE p.stockDisponible > 0 AND p.activo = true")
    List<Producto> findProductosDisponibles();
    
    @Query("SELECT p FROM Producto p WHERE p.precio <= :precioMax AND p.activo = true")
    List<Producto> findProductosPorPrecioMaximo(@Param("precioMax") BigDecimal precioMax);
} 