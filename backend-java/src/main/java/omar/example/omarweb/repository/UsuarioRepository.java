package omar.example.omarweb.repository;

import omar.example.omarweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos personalizados si los necesitas
    // Por ejemplo, buscar por email
    Usuario findByEmail(String email);
} 