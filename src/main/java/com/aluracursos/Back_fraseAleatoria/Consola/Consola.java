package com.aluracursos.Back_fraseAleatoria.Consola;

import com.aluracursos.Back_fraseAleatoria.Modelos.Libros;
import com.aluracursos.Back_fraseAleatoria.Servicios.ConsumoAPI;

import java.util.List;
import java.util.Scanner;

public class Consola {

    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion !=0 ) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Introduce el título del libro que deseas buscar:");
                    String titulo = input.nextLine();
                    List<Libros> librosEncontrados = consumoApi.buscarLibroTitulo(titulo);

                    // Mostrar los resultados
                    for (Libros libro : librosEncontrados) {
                        System.out.println("\nTítulo: " + libro.titulo());
                        System.out.println("Autor: " + libro.autor());        // Cambio aquí
                        System.out.println("Idioma: " + libro.idioma());      // Cambio aquí
                        System.out.println("Número de descargas: " + libro.numeroDescargas());
                        System.out.println("------------------------");
                    }
                    break;
                case 2:
                    consumoApi.listarLibroRegistrado();
                    break;
                case 3:
                    consumoApi.listarAutorRegistrado();
                    break;
                case 4:
                    consumoApi.listarAutorVivo();
                    break;
                case 5:
                    consumoApi.listarLibroPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}