package com.aluracursos.Back_fraseAleatoria.Servicios;
import com.aluracursos.Back_fraseAleatoria.Modelos.Libros;
import com.aluracursos.Back_fraseAleatoria.Modelos.LibrosResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ConsumoAPI {
    private static final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate = new RestTemplate();

    // Método para obtener todos los libros
    public String obtenerDatos() {
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
        validarRespuesta(response);
        return response.getBody();
    }

    // Método para busxar libro por título
    public List<Libros> buscarLibroTitulo(String titulo) {
        String url = API_URL + "?search=" + titulo;

        // Usamos exchange con ParameterizedTypeReference para manejar la respuesta como LibrosResponse
        ResponseEntity<LibrosResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LibrosResponse>() {}  // Referencia explícita del tipo
        );


        return response.getBody().results();  // Retorna la lista de libros desde la respuesta
    }

    public void listarLibroRegistrado() {
        System.out.println("Listando libro registrado");
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

    // Método auxiliar para validar respuesta HTTP
    private void validarRespuesta(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error en la solicitud: " + response.getStatusCode());
        }
    }
}
