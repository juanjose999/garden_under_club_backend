package com.garden.proyect;

import com.garden.proyect.entities.Usuario;
import com.garden.proyect.repositories.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProyectApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProyectApplication.class, args);

	}

}
