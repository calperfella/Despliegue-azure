package com.spring.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> bienvenido() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Bienvenidos al sistema de venta de libros");
        return response;
    }
}
