package com.spring.repositorios;

import com.spring.modelos.TarjetaMembresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarjetaMembresiaRepository extends JpaRepository<TarjetaMembresia, Long> {
    Optional<TarjetaMembresia> findByNumeroTarjeta(String numeroTarjeta);

}
