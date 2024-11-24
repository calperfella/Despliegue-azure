package com.spring.servicios;

import com.spring.modelos.Usuario;
import com.spring.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getUsuarioByTarjetaMembresiaId(Long tarjetaMembresiaId) {
        return usuarioRepository.findByTarjetaMembresiaId(tarjetaMembresiaId);
    }


    public Usuario saveOrUpdateUsuario(Usuario usuario) {
        if (usuario.getContraseña() == null || usuario.getContraseña().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        return usuarioRepository.save(usuario);
    }

}