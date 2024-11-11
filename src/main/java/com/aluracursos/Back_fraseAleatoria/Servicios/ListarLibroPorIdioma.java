package com.aluracursos.Back_fraseAleatoria.Servicios;

import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ListarLibroPorIdioma {

    @Autowired
    private LibroRepositorio libroRepositorio;

    private Scanner idioma = new Scanner(System.in);

    public void libroPorIdioma() {

        List<LibroDB> libros = libroRepositorio.findAll();
        System.out.println("""
                -----------------------------------------------
                Ingrese el idioma del libro que desea buscar:
                
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                -----------------------------------------------
                """);

        String input = idioma.nextLine().toLowerCase();
        boolean libroEncontrado = false;

        for (LibroDB libro : libros) {
            if (input.equals(libro.getIdioma().toLowerCase())) {
                System.out.println("----------LIBRO---------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("------------------------");

                libroEncontrado = true;
            }

        }

        if (!libroEncontrado) {
            System.out.println("No se encontraron libros por el idioma solicitado");
        }

    }
}
