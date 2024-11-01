package com.aluracursos.Back_fraseAleatoria.Servicios;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ConsumoAPI {
    private static final String API_URL = "https://gutendex.com/books";

    public String obtenerDatos() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error en la solicitud: " + response.getStatusCode());
        }
    }
}
