package com.spring.modelos;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tarjeta_membresia_id", nullable = false)
    private TarjetaMembresia tarjetaMembresia;

    @Column(nullable = false)
    private double totalCompra;

    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private String detallesCompra;
}
