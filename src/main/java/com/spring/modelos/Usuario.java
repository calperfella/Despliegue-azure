package com.spring.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "profesion")
    private String profesion;

    @Column(name = "tarjeta_membresia_id")
    private Long tarjetaMembresiaId;
}
