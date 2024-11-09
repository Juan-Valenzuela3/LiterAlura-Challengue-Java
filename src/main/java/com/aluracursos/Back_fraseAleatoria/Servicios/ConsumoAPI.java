package com.aluracursos.Back_fraseAleatoria.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumoAPI {

    @Autowired
    private BuscarLibro buscarLibro;

    @Autowired
    private ListarAutorVivo listarAutorVivo;

    @Autowired
    private ListarAutorRegistrado listarAutorRegistrado;

    public void buscarYGuardarLibro(String titulo) {
        buscarLibro.buscar_GuardarLibro(titulo);
    }

    public void listarLibrosRegistrados() {
        buscarLibro.listarLibroRegistrado();
    }

    public void listarAutorVivo() {
        listarAutorVivo.listarAutorVivo();
    }

    public void autoresRegistrados() {
        listarAutorRegistrado.autorRegistrado();
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


