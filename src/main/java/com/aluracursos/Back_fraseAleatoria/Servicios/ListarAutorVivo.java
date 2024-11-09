package com.aluracursos.Back_fraseAleatoria.Servicios;

import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ListarAutorVivo {

    @Autowired
    private LibroRepositorio libroRepositorio;

    private Scanner anioAutor = new Scanner(System.in);

    public void listarAutorVivo() {
        List<LibroDB> libros = libroRepositorio.findAll();

        System.out.println("Ingresa el año del autor que deseas buscar:");
        String input = anioAutor.nextLine();

        try {
            int anio = Integer.parseInt(input);  // Convierte el año ingresado a entero

            boolean autorEncontrado = false;  // Inicializamos en `false`

            for (LibroDB libro : libros) {
                Integer anioNacimiento = libro.getAnioNacimiento();
                Integer anioFallecimiento = libro.getAnioFallecimiento();

                // Verificamos si el autor estaba vivo en el año especificado
                if (anioNacimiento != null && anio >= anioNacimiento &&
                        (anioFallecimiento == null || anio <= anioFallecimiento)) {

                    // Imprimimos la información del autor encontrado
                    System.out.println("Autor: " + libro.getAutor());
                    System.out.println("Año de nacimiento: " + anioNacimiento);
                    System.out.println("Año de fallecimiento: " + (anioFallecimiento != null ? anioFallecimiento : "Aún vivo"));
                    System.out.println("Libro: " + libro.getTitulo());
                    System.out.println("--------------------------------------");

                    autorEncontrado = true;  // Marcamos que se encontró al menos un autor
                }
            }

            // Después del bucle, revisamos si se encontró algún autor
            if (!autorEncontrado) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            }

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingresa un año válido.");
        }
    }
}