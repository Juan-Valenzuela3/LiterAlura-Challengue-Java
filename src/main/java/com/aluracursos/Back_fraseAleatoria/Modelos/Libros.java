package com.aluracursos.Back_fraseAleatoria.Modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true)
public record Libros(
        @JsonAlias("title") String titulo,
        @JsonProperty("authors") List<Autor> autores,
        @JsonProperty("languages") List<String> idiomas,
        @JsonAlias("download_count") int numeroDescargas
) {
    @JsonCreator
    public Libros(
            @JsonProperty("title") String titulo,
            @JsonProperty("authors") List<Autor> autores,
            @JsonProperty("languages") List<String> idiomas,
            @JsonProperty("download_count") int numeroDescargas
    ) {
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
    }
}