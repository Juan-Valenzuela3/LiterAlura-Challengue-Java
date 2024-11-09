package com.aluracursos.Back_fraseAleatoria.Servicios;

import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ListarAutorRegistrado {

    @Autowired
    private LibroRepositorio libroRepositorio;

    public void autorRegistrado() {
        List<LibroDB> libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("-------AUTORES REGISTRADOS-------");
            for (LibroDB libro : libros) {
                System.out.println("\nNombre: " + libro.getAutor());
                System.out.println("Año de nacimiento: " + libro.getAnioNacimiento());
                System.out.println("Año de fallecimiento: " + libro.getAnioFallecimiento());
                System.out.println(String.format("Libro: [%s]", libro.getAutor()));
                System.out.println("--------------------------------------");
            }
        }
    }
}
