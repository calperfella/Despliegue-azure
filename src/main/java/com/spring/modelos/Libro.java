package com.spring.modelos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "unidades_disponibles", nullable = false)
    private int unidadesDisponibles;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;
}