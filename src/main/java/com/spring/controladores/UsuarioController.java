package com.spring.controladores;

import com.spring.modelos.Compra;
import com.spring.modelos.Usuario;
import com.spring.repositorios.CompraRepository;
import com.spring.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")

public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompraRepository compraRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    // Actualizamos solo los campos no nulos del usuario
                    if (usuarioActualizado.getNombre() != null) {
                        usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    }
                    if (usuarioActualizado.getCiudad() != null) {
                        usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
                    }
                    if (usuarioActualizado.getPais() != null) {
                        usuarioExistente.setPais(usuarioActualizado.getPais());
                    }
                    if (usuarioActualizado.getEdad() > 0) {
                        usuarioExistente.setEdad(usuarioActualizado.getEdad());
                    }
                    if (usuarioActualizado.getSexo() != null) {
                        usuarioExistente.setSexo(usuarioActualizado.getSexo());
                    }
                    if (usuarioActualizado.getProfesion() != null) {
                        usuarioExistente.setProfesion(usuarioActualizado.getProfesion());
                    }
                    if (usuarioActualizado.getTarjetaMembresiaId() != null) {
                        usuarioExistente.setTarjetaMembresiaId(usuarioActualizado.getTarjetaMembresiaId());
                    }
                    if (usuarioActualizado.getCorreo() != null) {
                        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
                    }
                    if (usuarioActualizado.getCelular() != null) {
                        usuarioExistente.setCelular(usuarioActualizado.getCelular());
                    }
                    if (usuarioActualizado.getDireccion() != null) {
                        usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
                    }
                    if (usuarioActualizado.getIdentificacionNacional() != null) {
                        usuarioExistente.setIdentificacionNacional(usuarioActualizado.getIdentificacionNacional());
                    }
                    if (usuarioActualizado.getContraseña() != null) {
                        usuarioExistente.setContraseña(usuarioActualizado.getContraseña());
                    }

                    // Guardamos el usuario actualizado y devolvemos la respuesta
                    Usuario usuarioGuardado = usuarioRepository.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioGuardado);
                })
                // Si el usuario no existe, devolvemos un 404
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String correo = credentials.get("correo");
        String contraseña = credentials.get("contraseña");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getContraseña().equals(contraseña)) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping("/compras")
    public ResponseEntity<?> listarComprasUsuario(@RequestParam Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            List<Compra> compras = compraRepository.findByUsuario(usuario);
            return ResponseEntity.ok(compras);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado con éxito.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }



}

