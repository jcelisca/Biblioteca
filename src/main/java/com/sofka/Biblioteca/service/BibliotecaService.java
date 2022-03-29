package com.sofka.Biblioteca.service;

import com.sofka.Biblioteca.model.Biblioteca;
import com.sofka.Biblioteca.repository.BibliotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository repository;

    public Biblioteca save(Biblioteca biblioteca){
        return repository.save(biblioteca);
    }
    public List<Biblioteca> findAll(){
        return repository.findAll();
    }

    public Optional<Biblioteca> findById(String id){
        return repository.findById(id);
    }

    public List<Biblioteca> findByCategoria(String categoria){
        return repository.findByCategoria(categoria);
    }

    public String consultarDisponibilidad(String id){
        Biblioteca recurso = repository.findById(id).get();
        if(recurso.getDisponibilidad() > 0 ){
            return "Hay disponibles "+recurso.getDisponibilidad()+" libros de "+recurso.getName()+" " ;
        }
        return "No hay disponibles. El último ejemplar se prestó el "+recurso.getFechaPrestamo();
    }

    public String prestarRecurso(String id){
        Biblioteca recurso = repository.findById(id).get();
        if(recurso.getDisponibilidad() > 0 ){
            if(recurso.getEstado().equals("No prestado")){
                recurso.setId(id);
                recurso.setName(recurso.getName());
                recurso.setCategoria(recurso.getCategoria());
                recurso.setDisponibilidad(recurso.getDisponibilidad()-1);
                recurso.setEstado("Prestado");
                recurso.setFechaPrestamo(LocalDate.now());
                save(recurso);
                return "Se presta el libro: "+recurso.getName()+". Quedan: "+recurso.getDisponibilidad();
            }
            recurso.setId(id);
            recurso.setName(recurso.getName());
            recurso.setCategoria(recurso.getCategoria());
            recurso.setEstado(recurso.getEstado());
            recurso.setFechaPrestamo(LocalDate.now());
            recurso.setDisponibilidad(recurso.getDisponibilidad()-1);
            save(recurso);
            return "Se presta el libro "+recurso.getName()+". Quedan: "+recurso.getDisponibilidad();
        }
        return "No hay disponibles. El último ejemplar se prestó el "+recurso.getFechaPrestamo();
    }

    public List<Biblioteca> recomendacion(String categoria){
        return repository.findByCategoria(categoria);
    }

    public String devolverRecurso(String id){
        Biblioteca recurso = repository.findById(id).get();
        if(recurso.getEstado().equals("Prestado")){
            recurso.setId(id);
            recurso.setCategoria(recurso.getCategoria());
            recurso.setName(recurso.getName());
            recurso.setFechaPrestamo(recurso.getFechaPrestamo());
            recurso.setDisponibilidad(recurso.getDisponibilidad()+1);
            recurso.setEstado("No prestado");
            save(recurso);
            return "Se devolvio el libro "+recurso.getName();
        }
        return "El recurso no se puede devolver porque no se encuentra en préstamo";
    }

}
