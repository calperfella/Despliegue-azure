package com.spring.servicios;

import com.spring.modelos.Compra;
import com.spring.repositorios.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public Compra saveCompra(Compra compra) {
        return compraRepository.save(compra);
    }
}
