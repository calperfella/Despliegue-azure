package com.spring.modelos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "tarjetas_membresia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarjetaMembresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_tarjeta", nullable = false, unique = true)
    private String numeroTarjeta;

    @Column(name = "saldo", nullable = false)
    private double saldo;
}
