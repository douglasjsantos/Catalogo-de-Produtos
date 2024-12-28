package com.dscatalog.dscatalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DscatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscatalogApplication.class, args);
	}


}
