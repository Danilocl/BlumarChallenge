package com.br.blumar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Blumar Turismos API", version = "1.0", description = "API de checkIn de quartos"))
public class BlumarChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlumarChallengeApplication.class, args);
	}

}
