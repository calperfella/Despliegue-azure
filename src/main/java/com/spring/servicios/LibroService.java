package com.spring.servicios;

import com.spring.modelos.Libro;
import com.spring.repositorios.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }

    public Optional<Libro> getLibroByTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    public Optional<Libro> getLibroByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }

    public Libro saveOrUpdateLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteLibroById(Long id) {
        libroRepository.deleteById(id);
    }
}
