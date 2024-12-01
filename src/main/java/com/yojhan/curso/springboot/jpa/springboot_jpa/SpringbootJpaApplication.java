package com.yojhan.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.yojhan.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.yojhan.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.yojhan.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {
	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		personalizedQueriesDistinc();
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinc() {
		System.out.println("================= Consultas con nombres de personas =================");
		List<String> names = repository.findAllName();
		names.forEach(System.out::println);

		System.out.println("================= Consulta con nombres unicos de personas =================");
		List<String> names2 = repository.findAllNameDistinc();
		names2.forEach(System.out::println);

		System.out.println("================= Consulta con lenguajes unicos =================");
		List<String> lenguage = repository.findAllLenguageDistinc();
		lenguage.forEach(System.out::println);

		System.out.println("================= Consulta con total de lenguages de programacion =================");
		Long lenguage2 = repository.findAllLenguageDistinctCount();
		System.out.println("Total de lenguajes repetidos: " + lenguage2);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {
		System.out.println("Consulta que puebla el objeto dto de una clase dto personalizada");
		List<PersonDto> persons = repository.findAllPersonDto();
		persons.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("======================= Consulta nombre por el id =======================");
		System.out.println("Ingresa el id para sacar su name");
		Long id = scanner.nextLong();

		String name = repository.nameById(id);
		System.out.println(name);

		System.out.println("======================= Consulta nombre completo por el id =======================");

		String name2 = repository.nameFullById(id);
		System.out.println(name2);
		scanner.close();
	}

	@Transactional
	public void eliminar2() {
		repository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona que quiere eliminar");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(p -> repository.delete(p), () -> System.out.println("No existe ese id"));

		scanner.close();
	}

	@Transactional
	public void eliminar() {
		repository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona que quiere eliminar");
		Long id = scanner.nextLong();

		repository.deleteById(id);
		repository.findAll().forEach(System.out::println);

		scanner.close();
	}

	@Transactional
	public void modificar() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el numero de id que quiere modificar");
		Long id = scanner.nextLong();
		AtomicBoolean b = new AtomicBoolean(false);
		/*
		 * Optional<Person> newUpdate = repository.findById(id);
		 * 
		 * 
		 * forma 1
		 * newUpdate.ifPresent(p -> {
		 * System.out.println(p);
		 * System.out.println("Ingrese el lenguaje de programacion nuevo");
		 * String lenguage = scanner.next();
		 * p.setProgrammingLenguage(lenguage);
		 * Person personDb = repository.save(p);
		 * System.out.println( personDb);
		 * 
		 * });
		 */

		// Forma 2
		repository.findById(id).ifPresent(p -> {
			b.set(true);
			System.out.println(p);
			System.out.println("Ingrese el lenguaje de programacion nuevo");
			String lenguage = scanner.next();
			p.setProgrammingLenguage(lenguage);
			repository.save(p);
			System.out.println("LOS DATOS QUEDARON: " + p);
		});
		if (b.get() == false) {
			System.out.println("No existe ese usuario");
		}

		scanner.close();

	}

	@Transactional
	public void guardar() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre");
		String name = scanner.next();
		System.out.println("Ingrese el apellido");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programacion");
		String lenguage = scanner.next();
		Person person = new Person(null, name, lastname, lenguage);
		Person perso = repository.save(person);
		System.out.println(perso);
		scanner.close();

		repository.findById(perso.getId()).ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void findOne() {

		/*
		 * Person persons = null;
		 * Optional<Person> personPres = repository.findById(20L);
		 * if (personPres.isPresent()) {
		 * persons = personPres.get();
		 * } else {
		 * System.out.println("No existe");
		 * }
		 * System.out.println(persons);
		 */
		// repository.findById(1L).ifPresent(person -> System.out.println(person));

		repository.findByNameContaining("An").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void list() {
		// Lista todas las personas
		// List<Person> persons = (List<Person>) repository.findAll();

		// Lista todas las personas que tenga java
		// List<Person> persons = (List<Person>)
		// repository.findByProgrammingLenguage("java");

		// lista a la persona con el id 2
		// Optional<Person> persons = repository.findById(2L);

		// lista mediante metodo personalizado
		// List<Person> persons = (List<Person>)
		// repository.buscarByProgrammingLenguage("java");

		// lista la persona con el nombre
		// List<Person> persons = (List<Person>) repository.findByName("Michael");

		List<Person> persons = (List<Person>) repository.findByProgrammingLenguageAndName("JavaScript", "Michael");

		persons.stream().forEach(person -> System.out.println(person));// no es necesario los {}

		List<Object[]> persons2 = repository.obtenerPersonaData();
		persons2.stream().forEach(person -> System.out.println(person[0] + " " + person[1]));// no es necesario los {}
	}

}
