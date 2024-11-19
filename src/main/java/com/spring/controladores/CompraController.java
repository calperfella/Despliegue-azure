package com.spring.controladores;

import com.spring.modelos.Compra;
import com.spring.modelos.CompraRequest;
import com.spring.repositorios.CompraRepository;
import com.spring.servicios.CompraService;
import com.spring.servicios.LibroService;
import com.spring.servicios.TarjetaMembresiaService;
import com.spring.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TarjetaMembresiaService tarjetaMembresiaService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private CompraRepository compraRepository;

    @GetMapping
    public ResponseEntity<List<Compra>> listarCompras() {
        List<Compra> compras = compraRepository.findAll();
        return ResponseEntity.ok(compras);
    }

    @PostMapping
    public String comprarLibros(@RequestParam Long usuarioId, @RequestParam String numeroTarjeta, @RequestBody CompraRequest compraRequest) {
        var tarjetaMembresiaOpt = tarjetaMembresiaService.getTarjetaMembresiaByNumero(numeroTarjeta);
        if (tarjetaMembresiaOpt.isEmpty()) {
            return "Número de tarjeta de membresía inválido.";
        }
        var tarjetaMembresia = tarjetaMembresiaOpt.get();

        var usuarioOpt = usuarioService.getUsuarioById(usuarioId);
        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getTarjetaMembresiaId().equals(tarjetaMembresia.getId())) {
            return "Usuario o tarjeta de membresía no válidos.";
        }

        class TotalCompraWrapper {
            double totalCompra = 0;
        }

        TotalCompraWrapper totalCompraWrapper = new TotalCompraWrapper();

        String detallesCompra = compraRequest.getLibros().stream().map(libroCompra -> {
            var libroOpt = libroService.getLibroById(libroCompra.getLibroId());
            if (libroOpt.isEmpty()) {
                throw new IllegalArgumentException("Libro con ID " + libroCompra.getLibroId() + " no encontrado.");
            }
            var libro = libroOpt.get();
            if (libro.getUnidadesDisponibles() < libroCompra.getCantidad()) {
                throw new IllegalArgumentException("No hay suficientes unidades del libro " + libro.getTitulo() + ".");
            }
            totalCompraWrapper.totalCompra += libro.getPrecio() * libroCompra.getCantidad();
            libro.setUnidadesDisponibles(libro.getUnidadesDisponibles() - libroCompra.getCantidad());
            libroService.saveOrUpdateLibro(libro);
            return libro.getTitulo() + " x" + libroCompra.getCantidad();
        }).collect(Collectors.joining(", "));

        if (tarjetaMembresia.getSaldo() < totalCompraWrapper.totalCompra) {
            return "Saldo insuficiente en la tarjeta de membresía.";
        }

        tarjetaMembresia.setSaldo(tarjetaMembresia.getSaldo() - totalCompraWrapper.totalCompra);
        tarjetaMembresiaService.saveOrUpdateTarjeta(tarjetaMembresia);

        Compra compra = new Compra();
        compra.setUsuario(usuarioOpt.get());
        compra.setTarjetaMembresia(tarjetaMembresia);
        compra.setTotalCompra(totalCompraWrapper.totalCompra);
        compra.setFechaCompra(LocalDateTime.now());
        compra.setDetallesCompra(detallesCompra);
        compraService.saveCompra(compra);

        return "Compra realizada exitosamente.";
    }
}
