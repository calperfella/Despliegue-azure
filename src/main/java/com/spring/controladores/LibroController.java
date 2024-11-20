package com.spring.controladores;

import com.spring.modelos.Libro;
import com.spring.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> getAllLibros() {
        return libroService.getAllLibros();
    }

    @GetMapping("/{id}")
    public Optional<Libro> getLibroById(@PathVariable Long id) {
        return libroService.getLibroById(id);
    }

    @GetMapping("/titulo/{titulo}")
    public Optional<Libro> getLibroByTitulo(@PathVariable String titulo) {
        return libroService.getLibroByTitulo(titulo);
    }

    @GetMapping("/isbn/{isbn}")
    public Optional<Libro> getLibroByIsbn(@PathVariable String isbn) {
        return libroService.getLibroByIsbn(isbn);
    }

    @PostMapping
    public ResponseEntity<Libro> createLibro(@RequestBody Libro libro) {
        Libro nuevoLibro = libroService.saveOrUpdateLibro(libro);
        return ResponseEntity.ok(nuevoLibro);
    }

    // Método para actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        Optional<Libro> libroExistente = libroService.getLibroById(id);
        if (libroExistente.isPresent()) {
            Libro libroActualizado = libroExistente.get();
            libroActualizado.setTitulo(libro.getTitulo());
            libroActualizado.setIsbn(libro.getIsbn());
            libroActualizado.setPrecio(libro.getPrecio());
            libroActualizado.setUnidadesDisponibles(libro.getUnidadesDisponibles());
            libroActualizado.setImagenUrl(libro.getImagenUrl());
            libroService.saveOrUpdateLibro(libroActualizado);
            return ResponseEntity.ok(libroActualizado);
        } else {
            return ResponseEntity.badRequest().body("Libro no encontrado.");
        }
    }

    // Método para eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id) {
        Optional<Libro> libroExistente = libroService.getLibroById(id);
        if (libroExistente.isPresent()) {
            libroService.deleteLibroById(id);
            return ResponseEntity.ok("Libro eliminado con éxito.");
        } else {
            return ResponseEntity.badRequest().body("Libro no encontrado.");
        }
    }
}
