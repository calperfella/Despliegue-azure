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

        // Verificar y guardar usuarios
        saveUsuarioIfNotExists(new Usuario(
                null,
                "Juan Pérez",
                "Bogotá",
                "Colombia",
                30,
                "Masculino",
                "Ingeniero",
                null, // Esto será actualizado después de guardar las tarjetas
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
                null, // Esto será actualizado después de guardar las tarjetas
                "maria.gomez@gmail.com",
                "0987654321",
                "Carrera 89 #10-11",
                "987654321"
        ));
    }

    private void saveLibroIfNotExists(Libro libro) {
        if (!libroRepository.existsByIsbn(libro.getIsbn())) {
            libroRepository.save(libro);
        }
    }

    private void saveTarjetaIfNotExists(TarjetaMembresia tarjeta) {
        if (!tarjetaMembresiaRepository.existsByNumeroTarjeta(tarjeta.getNumeroTarjeta())) {
            tarjetaMembresiaRepository.save(tarjeta);
        }
    }

    private void saveUsuarioIfNotExists(Usuario usuario) {
        if (!usuarioRepository.existsByCorreo(usuario.getEmail())) {
            usuarioRepository.save(usuario);
        }
    }
}
