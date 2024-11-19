package com.spring.repositorios;

import com.spring.modelos.Compra;
import com.spring.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuario(Usuario usuario);
}
