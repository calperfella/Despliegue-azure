package com.spring.controladores;

import com.spring.modelos.TarjetaMembresia;
import com.spring.repositorios.TarjetaMembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tarjetas")
public class TarjetaMembresiaController {

    @Autowired
    private TarjetaMembresiaRepository tarjetaMembresiaRepository;

    @GetMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestParam String numeroTarjeta) {
        Optional<TarjetaMembresia> tarjetaOpt = tarjetaMembresiaRepository.findByNumeroTarjeta(numeroTarjeta);

        if (tarjetaOpt.isPresent()) {
            TarjetaMembresia tarjeta = tarjetaOpt.get();
            return ResponseEntity.ok(tarjeta.getSaldo());
        } else {
            return ResponseEntity.badRequest().body("Tarjeta de membresía no encontrada.");
        }
    }

    @PutMapping("/recargar")
    public ResponseEntity<?> recargarTarjeta(@RequestParam String numeroTarjeta, @RequestParam double monto) {
        Optional<TarjetaMembresia> tarjetaOpt = tarjetaMembresiaRepository.findByNumeroTarjeta(numeroTarjeta);

        if (tarjetaOpt.isPresent()) {
            TarjetaMembresia tarjeta = tarjetaOpt.get();

            if (monto < 50000 || monto > 200000) {
                return ResponseEntity.badRequest().body("El monto debe estar entre 50,000 y 200,000.");
            }

            tarjeta.setSaldo(tarjeta.getSaldo() + monto);
            tarjetaMembresiaRepository.save(tarjeta);

            return ResponseEntity.ok("Tarjeta recargada con éxito.");
        } else {
            return ResponseEntity.badRequest().body("Tarjeta de membresía no encontrada.");
        }
    }

}
