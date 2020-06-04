package me.yorick.poc.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPersistenceApplication.class, args);
	}

}
