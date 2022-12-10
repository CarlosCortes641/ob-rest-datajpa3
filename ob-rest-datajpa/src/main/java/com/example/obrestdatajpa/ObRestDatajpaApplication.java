package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entitites.Laptop;
import com.example.obrestdatajpa.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		// CRUD
		// crear laptop
		Laptop laptop1 = new Laptop(null, "Apple", "Air", 5, 3.500,true);
		Laptop laptop2 = new Laptop(null, "Lenovo", "Idepad", 4, 2.500, false);

		// almacenar un laptop
		System.out.println("Número de laptops en base de datos: " + repository.findAll().size());
		repository.save(laptop1);
		repository.save(laptop2);

		// recuperar todos los laptops
		System.out.println("Número de laptops en base de datos: " + repository.findAll().size());

		// borrar un laptop
		//repository.deleteById(1L);
		System.out.println("Número de laptops en base de datos: " + repository.findAll().size());

	}

}
