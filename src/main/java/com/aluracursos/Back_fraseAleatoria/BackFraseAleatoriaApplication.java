package com.aluracursos.Back_fraseAleatoria;

import com.aluracursos.Back_fraseAleatoria.Consola.Consola;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BackFraseAleatoriaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BackFraseAleatoriaApplication.class, args);

		// Iniciar la consola
		Consola consola = context.getBean(Consola.class);
		consola.mostrarMenu();
	}

}
