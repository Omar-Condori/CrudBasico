package omar.example.omarweb.repository;

import omar.example.omarweb.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Métodos personalizados
    List<Categoria> findByActivaTrue();
    Categoria findByNombre(String nombre);
} 