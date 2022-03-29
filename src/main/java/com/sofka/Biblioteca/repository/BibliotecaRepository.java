package com.sofka.Biblioteca.repository;

import com.sofka.Biblioteca.model.Biblioteca;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BibliotecaRepository extends MongoRepository<Biblioteca, String> {
    List<Biblioteca> findByCategoria(String categoria);
}
