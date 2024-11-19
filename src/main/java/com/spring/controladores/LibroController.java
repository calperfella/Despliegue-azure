package com.spring.controladores;

import com.spring.modelos.Libro;
import com.spring.servicios.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
