package com.aluracursos.Back_fraseAleatoria.Modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibrosResponse(List<Libros> results) {}
