package com.aluracursos.Back_fraseAleatoria.Servicios;

import com.aluracursos.Back_fraseAleatoria.Modelos.Autor;
import com.aluracursos.Back_fraseAleatoria.Modelos.Libros;
import com.aluracursos.Back_fraseAleatoria.Modelos.LibrosResponse;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroRepositorio;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
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
            String url = API_URL + "?search=" + titulo;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<LibrosResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<LibrosResponse>() {}
            );

            List<Libros> resultados = response.getBody().results();
            if (resultados.isEmpty()) {
                return null;
            }

            Libros libroApi = resultados.get(0);
            List<LibroDB> libroExistente = libroRepositorio.findByTituloEqualsIgnoreCase(libroApi.titulo());

            if (!libroExistente.isEmpty()) {
                System.out.println("Libro encontrado en la base de datos local.");
                return libroExistente.get(0);
            }

            LibroDB libroDB = new LibroDB();
            libroDB.setTitulo(libroApi.titulo());
            libroDB.setAutor(libroApi.autores().isEmpty() ? "Desconocido" : libroApi.autores().get(0).nombre());
            libroDB.setIdioma(libroApi.idiomas().isEmpty() ? "Desconocido" : libroApi.idiomas().get(0));
            libroDB.setNumeroDescargas(libroApi.numeroDescargas());

            // Extraer años de nacimiento y fallecimiento del primer autor en la lista
            if (!libroApi.autores().isEmpty()) {
                Autor autor = libroApi.autores().get(0);
                libroDB.setAnioNacimiento(autor.anioNacimiento());
                libroDB.setAnioFallecimiento(autor.anioFallecimiento());
            } else {
                libroDB.setAnioNacimiento(null);
                libroDB.setAnioFallecimiento(null);
            }

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
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("-------LIBROS REGISTRADOS-------");
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
        List<LibroDB> libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("-------AUTORES REGISTRADOS-------");
            for (LibroDB libro : libros) {
                System.out.println("\nNombre: " + libro.getAutor());
                System.out.println("Año de nacimiento: " + libro.getAnioNacimiento());
                System.out.println("Año de fallecimiento: " + libro.getAnioFallecimiento());
                System.out.println("--------------------------------------");
            }
        }
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