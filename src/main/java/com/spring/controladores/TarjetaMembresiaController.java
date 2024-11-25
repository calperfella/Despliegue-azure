package com.spring.controladores;

import com.spring.modelos.TarjetaMembresia;
import com.spring.repositorios.TarjetaMembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarjetas")
@CrossOrigin(origins = "http://localhost:4200")

public class TarjetaMembresiaController {

    @Autowired
    private TarjetaMembresiaRepository tarjetaMembresiaRepository;

    @GetMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@RequestParam String numeroTarjeta) {
        Optional<TarjetaMembresia> tarjetaOpt = tarjetaMembresiaRepository.findByNumeroTarjeta(numeroTarjeta);
        if (tarjetaOpt.isPresent()) {
            return ResponseEntity.ok(tarjetaOpt.get().getSaldo());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarjeta de membresía no encontrada.");
    }

    @PutMapping("/recargar")
    public ResponseEntity<?> recargarTarjeta(
            @RequestParam String numeroTarjeta,
            @RequestParam double monto) {
        Optional<TarjetaMembresia> tarjetaOpt = tarjetaMembresiaRepository.findByNumeroTarjeta(numeroTarjeta);

        if (tarjetaOpt.isPresent()) {
            if (monto < 50000 || monto > 200000) {
                return ResponseEntity.badRequest().body("El monto debe estar entre 50,000 y 200,000.");
            }

            TarjetaMembresia tarjeta = tarjetaOpt.get();
            tarjeta.setSaldo(tarjeta.getSaldo() + monto);
            tarjetaMembresiaRepository.save(tarjeta);

            return ResponseEntity.ok("Tarjeta recargada con éxito. Nuevo saldo: " + tarjeta.getSaldo());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarjeta de membresía no encontrada.");
    }

    @GetMapping
    public ResponseEntity<?> listarTarjetas() {
        return ResponseEntity.ok(tarjetaMembresiaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTarjetaPorId(@PathVariable Long id) {
        Optional<TarjetaMembresia> tarjetaOpt = tarjetaMembresiaRepository.findById(id);
        if (tarjetaOpt.isPresent()) {
            return ResponseEntity.ok(tarjetaOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarjeta de membresía no encontrada.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarjeta(@PathVariable Long id) {
        if (tarjetaMembresiaRepository.existsById(id)) {
            tarjetaMembresiaRepository.deleteById(id);
            return ResponseEntity.ok("Tarjeta de membresía eliminada con éxito.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarjeta de membresía no encontrada.");
    }
}