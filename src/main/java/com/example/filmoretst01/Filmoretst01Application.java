package com.example.filmoretst01;

import com.example.filmoretst01.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Filmoretst01Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Filmoretst01Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
