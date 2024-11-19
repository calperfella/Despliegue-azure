package com.spring.modelos;

import lombok.Data;

import java.util.List;

@Data
public class CompraRequest {

    private List<LibroCompra> libros;

    @Data
    public static class LibroCompra {
        private Long libroId;
        private int cantidad;
    }
}