package com.aluracursos.Back_fraseAleatoria.Servicios;

import com.aluracursos.Back_fraseAleatoria.Modelos.Libros;
import com.aluracursos.Back_fraseAleatoria.Modelos.LibrosResponse;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroRepositorio;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumoAPI {

    private static final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LibroRepositorio libroRepositorio;

    @Autowired
    public ConsumoAPI(LibroRepositorio libroRepositorio) {
        this.libroRepositorio = libroRepositorio;
    }

    public String obtenerDatos() {
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
        validarRespuesta(response);
        return response.getBody();
    }

    public LibroDB buscarYGuardarLibroTitulo(String titulo) {
        try {
            // Primero buscar en la API para obtener el título exacto
            String url = API_URL + "?search=" + titulo;
            ResponseEntity<LibrosResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<LibrosResponse>() {}
            );

            List<Libros> resultados = response.getBody().results();
            if (resultados.isEmpty()) {
                return null;
            }

            Libros libroApi = resultados.get(0);

            // Buscar en la base de datos usando el título exacto de la API
            List<LibroDB> libroExistente = libroRepositorio.findByTituloEqualsIgnoreCase(libroApi.titulo());

            if (!libroExistente.isEmpty()) {
                System.out.println("Libro encontrado en la base de datos local.");
                return libroExistente.get(0);
            }

            // Si no existe, crear y guardar el nuevo libro
            LibroDB libroDB = new LibroDB();
            libroDB.setTitulo(libroApi.titulo());
            libroDB.setAutor(libroApi.autor());
            libroDB.setIdioma(libroApi.idioma());
            libroDB.setNumeroDescargas(libroApi.numeroDescargas());

            System.out.println("Guardando nuevo libro en la base de datos.");
            return libroRepositorio.save(libroDB);

        } catch (Exception e) {
            System.out.println("Error al buscar/guardar el libro: " + e.getMessage());
            return null;
        }
    }

    public void listarLibroRegistrado() {

        List<LibroDB> libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.printf("No hay libros registrados en la base de datos.");
        } else {
            System.out.printf("-------LIBROS REGISTRADOS-------");
            for (LibroDB libro : libros) {
                System.out.println("\nTítulo: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("--------------------------------------");
            }
        }
    }

    public void listarAutorRegistrado() {
        System.out.println("Listando autor registrado");
    }

    public void listarAutorVivo() {
        System.out.println("Listando autor vivo");
    }

    public void listarLibroPorIdioma() {
        System.out.println("Listando libro por idioma");
    }

    private void validarRespuesta(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error en la solicitud: " + response.getStatusCode());
        }
    }
}