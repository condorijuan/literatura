package com.juan.literatura;

import com.juan.literatura.Principal.Principal;
import com.juan.literatura.Repositorio.AutorRepository;
import com.juan.literatura.Repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

    @Autowired
    private LibroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Principal p = new Principal(repository, autorRepository);
        p.Menu();
    }
}
