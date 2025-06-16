package com.ii02735.learn_spring_security_6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableWebSecurity -> pas nécessaire de le mettre, Spring Boot est assez intelligent de l'activer !
public class LearnSpringSecurity6Application {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringSecurity6Application.class, args);
	}

}
