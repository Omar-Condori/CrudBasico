package omar.example.omarweb.controller;

import omar.example.omarweb.entity.Categoria;
import omar.example.omarweb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/activas")
    public List<Categoria> getCategoriasActivas() {
        return categoriaRepository.findByActivaTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> ResponseEntity.ok().body(categoria))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Categoria createCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaActualizada) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaActualizada.getNombre());
                    categoria.setDescripcion(categoriaActualizada.getDescripcion());
                    categoria.setActiva(categoriaActualizada.isActiva());
                    Categoria categoriaGuardada = categoriaRepository.save(categoria);
                    return ResponseEntity.ok().body(categoriaGuardada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoriaRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 