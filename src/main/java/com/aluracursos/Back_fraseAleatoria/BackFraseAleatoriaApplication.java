package com.aluracursos.Back_fraseAleatoria;

import com.aluracursos.Back_fraseAleatoria.Consola.Consola;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BackFraseAleatoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackFraseAleatoriaApplication.class, args);

		// Iniciar la consola
		Consola consola = new Consola();
		consola.mostrarMenu();
	}

}
