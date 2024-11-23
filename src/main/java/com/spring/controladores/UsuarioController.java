package com.spring.controladores;

import com.spring.modelos.Compra;
import com.spring.modelos.Usuario;
import com.spring.repositorios.CompraRepository;
import com.spring.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
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
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            Usuario errorUsuario = new Usuario();
            errorUsuario.setNombre("Usuario no encontrado");
            return ResponseEntity.badRequest().body(errorUsuario);
        }
    }



    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuarioExistente = usuarioOpt.get();

            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
            usuarioExistente.setPais(usuarioActualizado.getPais());
            usuarioExistente.setEdad(usuarioActualizado.getEdad());
            usuarioExistente.setSexo(usuarioActualizado.getSexo());
            usuarioExistente.setProfesion(usuarioActualizado.getProfesion());
            usuarioExistente.setTarjetaMembresiaId(usuarioActualizado.getTarjetaMembresiaId());
            usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            usuarioExistente.setCelular(usuarioActualizado.getCelular());
            usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
            usuarioExistente.setIdentificacionNacional(usuarioActualizado.getIdentificacionNacional());

            usuarioRepository.save(usuarioExistente);
            return ResponseEntity.ok(usuarioExistente);
        } else {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
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
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }
    }
}