package com.spring.controladores;

import com.spring.modelos.Libro;
import com.spring.modelos.TarjetaMembresia;
import com.spring.modelos.Usuario;
import com.spring.repositorios.LibroRepository;
import com.spring.repositorios.TarjetaMembresiaRepository;
import com.spring.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarjetaMembresiaRepository tarjetaMembresiaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar y guardar libros
        saveLibroIfNotExists(new Libro(null, "El Quijote", "123456789", 60000, 10, "https://images-na.ssl-images-amazon.com/images/I/61HOpyVqSeL.jpg"));
        saveLibroIfNotExists(new Libro(null, "Cien Años de Soledad", "987654321", 70000, 30, "https://www.rae.es/sites/default/files/portada_cien_anos_de_soledad_0.jpg"));
        saveLibroIfNotExists(new Libro(null, "La Odisea", "654321987", 80000, 15, "https://www.loqueleo.com/do/uploads/2021/08/resized/600_portada-la-odisea.jpg"));

        // Verificar y guardar tarjetas de membresía
        saveTarjetaIfNotExists(new TarjetaMembresia(null, "1111222233334444", 100000));
        saveTarjetaIfNotExists(new TarjetaMembresia(null, "5555666677778888", 150000));

        // Verificar y guardar usuarios con tarjetas asignadas
        saveUsuarioIfNotExists(new Usuario(
                null,
                "Juan Pérez",
                "Bogotá",
                "Colombia",
                30,
                "Masculino",
                "Ingeniero",
                findTarjetaByNumero("1111222233334444"),
                "juan.perez@gmail.com",
                "1234567890",
                "Calle 123 #45-67",
                "123456789"
        ));

        saveUsuarioIfNotExists(new Usuario(
                null,
                "María Gómez",
                "Medellín",
                "Colombia",
                25,
                "Femenino",
                "Doctora",
                findTarjetaByNumero("5555666677778888"),
                "maria.gomez@gmail.com",
                "0987654321",
                "Carrera 89 #10-11",
                "987654321"
        ));
    }

    private void saveLibroIfNotExists(Libro libro) {
        if (!libroRepository.existsByIsbn(libro.getIsbn())) {
            libroRepository.save(libro);
            System.out.println("Libro guardado: " + libro.getTitulo());
        } else {
            System.out.println("Libro ya existe: " + libro.getTitulo());
        }
    }

    private void saveTarjetaIfNotExists(TarjetaMembresia tarjeta) {
        if (!tarjetaMembresiaRepository.existsByNumeroTarjeta(tarjeta.getNumeroTarjeta())) {
            tarjetaMembresiaRepository.save(tarjeta);
            System.out.println("Tarjeta de membresía guardada: " + tarjeta.getNumeroTarjeta());
        } else {
            System.out.println("Tarjeta de membresía ya existe: " + tarjeta.getNumeroTarjeta());
        }
    }

    private void saveUsuarioIfNotExists(Usuario usuario) {
        if (!usuarioRepository.existsByCorreo(usuario.getEmail())) {
            if (usuario.getTarjetaMembresiaId() == null) {
                throw new IllegalArgumentException("El usuario " + usuario.getNombre() + " no tiene tarjeta de membresía asociada.");
            }
            usuarioRepository.save(usuario);
            System.out.println("Usuario guardado: " + usuario.getNombre());
        } else {
            System.out.println("Usuario ya existe: " + usuario.getNombre());
        }
    }

    private Long findTarjetaByNumero(String numeroTarjeta) {
        Optional<TarjetaMembresia> tarjeta = tarjetaMembresiaRepository.findByNumeroTarjeta(numeroTarjeta);
        return tarjeta.map(TarjetaMembresia::getId).orElseThrow(() ->
                new IllegalArgumentException("La tarjeta con número " + numeroTarjeta + " no existe."));
    }
}
