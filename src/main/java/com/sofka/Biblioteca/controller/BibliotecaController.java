package com.sofka.Biblioteca.controller;

import com.sofka.Biblioteca.model.Biblioteca;
import com.sofka.Biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BibliotecaController {

    @Autowired
    private BibliotecaService service;

    @PostMapping("/biblioteca")
    @ResponseStatus(HttpStatus.CREATED)
    public Biblioteca save (@RequestBody Biblioteca biblioteca){
        return service.save(biblioteca);
    }

    @GetMapping("/biblioteca")
    public List<Biblioteca> findAll(){
        return service.findAll();
    }

    @GetMapping("/biblioteca/{Id}/disponibilidad")
    public String consultarDisponibilidad(@PathVariable("Id") String id){
        return service.consultarDisponibilidad(id);
    }

    @GetMapping("/biblioteca/{Id}/prestar")
    public String prestarRecurso(@PathVariable("Id") String id){
        return service.prestarRecurso(id);
    }

    @GetMapping("/biblioteca/{categoria}/recomendar")
    public List<Biblioteca> recomendacion(@PathVariable("categoria") String categoria){
        return service.recomendacion(categoria);
    }

    @GetMapping("/biblioteca/{Id}/devolver")
    public String devolverRecurso(@PathVariable("Id") String id){
        return service.devolverRecurso(id);
    }

}
