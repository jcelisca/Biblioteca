package com.sofka.Biblioteca;

import com.sofka.Biblioteca.model.Biblioteca;
import com.sofka.Biblioteca.service.BibliotecaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BibliotecaControllerTest {

    @MockBean
    private BibliotecaService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / todos los recursos")
    void findAll() throws Exception{
        List<Biblioteca> list = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Libro");
        biblioteca.setEstado("No prestado");
        list.add(biblioteca);

        Mockito.when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/biblioteca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("tytyty5")))
                .andExpect(jsonPath("$[0].name", is("Libro")))
                .andExpect(jsonPath("$[0].estado", is("No prestado")));
    }

    @Test
    @DisplayName("GET / consultar disponibilidad")
    void consultarDisponibilidad() throws Exception{
        List<Biblioteca> list = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Penurias");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);
        list.add(biblioteca);

        Mockito.when(service.consultarDisponibilidad("tytyty5")).thenReturn("Hay disponibles 2 libros de Penurias");

        mockMvc.perform(get("/biblioteca/{Id}/disponibilidad","tytyty5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hay disponibles 2 libros de Penurias"));
    }

    @Test
    @DisplayName("GET / prestar recurso")
    void prestarRecurso() throws Exception{
        List<Biblioteca> list = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Penurias");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);
        list.add(biblioteca);

        Mockito.when(service.prestarRecurso("tytyty5")).thenReturn("Se presta el libro Penurias. Quedan: 1");

        mockMvc.perform(get("/biblioteca/{Id}/prestar","tytyty5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se presta el libro Penurias. Quedan: 1"));
    }

    @Test
    @DisplayName("GET / recomendar")
    void recomendacion() throws Exception{
        List<Biblioteca> list = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Penurias");
        biblioteca.setEstado("No prestado");
        biblioteca.setDisponibilidad(2);
        biblioteca.setCategoria("Romance");
        list.add(biblioteca);

        Mockito.when(service.recomendacion("Romance")).thenReturn(list);

        mockMvc.perform(get("/biblioteca/{categoria}/recomendar","Romance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("tytyty5")))
                .andExpect(jsonPath("$[0].name", is("Penurias")))
                .andExpect(jsonPath("$[0].disponibilidad", is(2)))
                .andExpect(jsonPath("$[0].categoria", is("Romance")))
                .andExpect(jsonPath("$[0].estado", is("No prestado")));
    }

    @Test
    @DisplayName("GET / devolver recurso")
    void devolverRecurso() throws Exception{
        List<Biblioteca> list = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId("tytyty5");
        biblioteca.setName("Penurias");

        Mockito.when(service.devolverRecurso("tytyty5")).thenReturn("Se devolvio el libro Penurias");

        mockMvc.perform(get("/biblioteca/{Id}/devolver","tytyty5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se devolvio el libro Penurias"));
    }

}
