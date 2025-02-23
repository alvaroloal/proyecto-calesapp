package com.salesianostriana.dam.calesapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				description = "Aplicación que:\n" +
						"Ofrece la ubicacion de las paradas\n" +
						"Permite a los cocheros ofrecer sus servicios.\n" +
						"Informa a los usuarios sobre los detalles de los servicios que ofrecen los cocheros.\n" +
						"Permite a los usuarios valorar el servicio ofrecido por cada uno de los cocheros.\n" +
						"Permite a los usuarios contactar con el cochero.\n",
				version = "1.0",
				contact = @Contact(
						email = "lorente.alalv24@triana.salesianos.edu",
						name = "Álvaro Lorente Almán"
				),
				license = @License(
						name = "Álvaro Lorente Almán"
				),
				title = "CALESAPP"
		)
)
public class CalesappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalesappApplication.class, args);
	}

}
