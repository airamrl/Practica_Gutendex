package com.aluracursos.Practica_Gutendex;

import com.aluracursos.Practica_Gutendex.principal.Principal;
import com.aluracursos.Practica_Gutendex.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticaGutendexApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PracticaGutendexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.mostrarMenu();




	}
}
