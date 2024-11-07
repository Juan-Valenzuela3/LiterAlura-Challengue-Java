package com.aluracursos.Back_fraseAleatoria.Consola;

import com.aluracursos.Back_fraseAleatoria.Modelos.Libros;
import com.aluracursos.Back_fraseAleatoria.Repository.LibroDB;
import com.aluracursos.Back_fraseAleatoria.Servicios.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Consola {

    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoApi;

    @Autowired
    public Consola(ConsumoAPI consumoApi) {
        this.consumoApi = consumoApi;
    }

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
                    LibroDB libroEncontrado = consumoApi.buscarYGuardarLibroTitulo(titulo);

                    // Mostrar los resultados
                    if (libroEncontrado != null) {
                        System.out.println("----------LIBRO---------");
                        System.out.println("\nTítulo: " + libroEncontrado.getTitulo());
                        System.out.println("Autor: " + libroEncontrado.getAutor());
                        System.out.println("Idioma: " + libroEncontrado.getIdioma());
                        System.out.println("Número de descargas: " + libroEncontrado.getNumeroDescargas());
                        System.out.println("ID en base de datos: " + libroEncontrado.getId());
                        System.out.println("------------------------");
                    } else {
                        System.out.println("No se encontró ningún libro con ese título.");
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
