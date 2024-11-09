package com.aluracursos.Back_fraseAleatoria.Servicios;

import com.aluracursos.Back_fraseAleatoria.Modelos.Autor;
import com.aluracursos.Back_fraseAleatoria.Modelos.Libros;
import com.aluracursos.Back_fraseAleatoria.Modelos.LibrosResponse;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;



@Service
public class BuscarLibro {

    private static final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private LibroRepositorio libroRepositorio;

    public void buscar_GuardarLibro(String titulo) {
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
                System.out.println("No se encontró ningún libro con ese título.");
                return;
            }

            Libros libroApi = resultados.get(0);
            List<LibroDB> libroExistente = libroRepositorio.findByTituloEqualsIgnoreCase(libroApi.titulo());

            LibroDB libroDB;
            if (!libroExistente.isEmpty()) {
                System.out.println("Libro encontrado en la base de datos local.");
                libroDB = libroExistente.get(0);
            } else {
                libroDB = new LibroDB();
                libroDB.setTitulo(libroApi.titulo());
                libroDB.setAutor(libroApi.autores().isEmpty() ? "Desconocido" : libroApi.autores().get(0).nombre());
                libroDB.setIdioma(libroApi.idiomas().isEmpty() ? "Desconocido" : libroApi.idiomas().get(0));
                libroDB.setNumeroDescargas(libroApi.numeroDescargas());

                if (!libroApi.autores().isEmpty()) {
                    libroDB.setAnioNacimiento(libroApi.autores().get(0).anioNacimiento());
                    libroDB.setAnioFallecimiento(libroApi.autores().get(0).anioFallecimiento());
                }

                libroDB = libroRepositorio.save(libroDB);
                System.out.println("Guardando nuevo libro en la base de datos.");
            }

            // Mostrar el libro encontrado o guardado
            System.out.println("----------LIBRO---------");
            System.out.println("Título: " + libroDB.getTitulo());
            System.out.println("Autor: " + libroDB.getAutor());
            System.out.println("Idioma: " + libroDB.getIdioma());
            System.out.println("Número de descargas: " + libroDB.getNumeroDescargas());
            System.out.println("ID en base de datos: " + libroDB.getId());
            System.out.println("------------------------");

        } catch (Exception e) {
            System.out.println("Error al buscar/guardar el libro: " + e.getMessage());
        }
    }

    public void listarLibroRegistrado() {
        List<LibroDB> libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("----------LIBROS REGISTRADOS----------");
            for (LibroDB libro : libros) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("--------------------------------------");
            }
        }
    }
}
