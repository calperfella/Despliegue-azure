package com.spring.servicios;

import com.spring.modelos.TarjetaMembresia;
import com.spring.repositorios.TarjetaMembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarjetaMembresiaService {

    @Autowired
    private TarjetaMembresiaRepository tarjetaMembresiaRepository;

    public Optional<TarjetaMembresia> getTarjetaMembresiaByNumero(String numeroTarjeta) {
        return tarjetaMembresiaRepository.findByNumeroTarjeta(numeroTarjeta);
    }

    public TarjetaMembresia saveOrUpdateTarjeta(TarjetaMembresia tarjetaMembresia) {
        return tarjetaMembresiaRepository.save(tarjetaMembresia);
    }
}