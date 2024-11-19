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

import java.util.Arrays;

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
        Libro libro1 = new Libro(null, "El Quijote", "123456789", 60000, 10, "https://images-na.ssl-images-amazon.com/images/I/61HOpyVqSeL.jpg");
        Libro libro2 = new Libro(null, "Cien Años de Soledad", "987654321", 70000, 30, "https://www.rae.es/sites/default/files/portada_cien_anos_de_soledad_0.jpg");
        Libro libro3 = new Libro(null, "La Odisea", "654321987", 80000, 15, "https://www.loqueleo.com/do/uploads/2021/08/resized/600_portada-la-odisea.jpg");

        libroRepository.saveAll(Arrays.asList(libro1, libro2, libro3));

        TarjetaMembresia tarjeta1 = new TarjetaMembresia(null, "1111222233334444", 100000);
        TarjetaMembresia tarjeta2 = new TarjetaMembresia(null, "5555666677778888", 150000);

        tarjetaMembresiaRepository.saveAll(Arrays.asList(tarjeta1, tarjeta2));

        Usuario usuario1 = new Usuario(null, "Juan Pérez", "Bogotá", "Colombia", 30, "Masculino", "Ingeniero", tarjeta1.getId());
        Usuario usuario2 = new Usuario(null, "María Gómez", "Medellín", "Colombia", 25, "Femenino", "Doctora", tarjeta2.getId());

        usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));
    }
}
