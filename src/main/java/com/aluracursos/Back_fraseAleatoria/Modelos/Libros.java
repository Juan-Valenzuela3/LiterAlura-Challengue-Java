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
        @JsonProperty("authors") @JsonAlias("authors") String autor,
        @JsonProperty("languages") @JsonAlias("languages") String idioma,
        @JsonAlias("download_count") int numeroDescargas
) {
    @JsonCreator
    public Libros(
            String titulo,
            @JsonProperty("authors") JsonNode authors,
            @JsonProperty("languages") JsonNode languages,
            int numeroDescargas
    ) {
        this(
                titulo,
                authors != null && !authors.isEmpty() ? authors.get(0).get("name").asText() : "Desconocido",
                languages != null && !languages.isEmpty() ? languages.get(0).asText() : "Desconocido",
                numeroDescargas
        );
    }
}