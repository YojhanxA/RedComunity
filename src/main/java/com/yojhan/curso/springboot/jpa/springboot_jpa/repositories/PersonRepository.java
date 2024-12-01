package com.yojhan.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yojhan.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.yojhan.curso.springboot.jpa.springboot_jpa.entities.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("select count(distinct(p.programmingLenguage)) from Person p")
    Long findAllLenguageDistinctCount();

    @Query("select distinct(p.programmingLenguage) from Person p")
    List<String> findAllLenguageDistinc();

    @Query("select p.name from Person p")
    List<String> findAllName();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllNameDistinc();

    // Toca colocar el package completo porque no esta en el contexto de
    // persistencia, no tiene anotacion
    @Query("select new com.yojhan.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select p.name from Person p where p.id=?1")
    String nameById(Long id);

    @Query("select concat(p.name, ' ', p.lastname) as fullName from Person p where p.id=?1")
    String nameFullById(Long id);

    List<Person> findByProgrammingLenguage(String programmingLenguage);

    // Consulta personalizada, para no usar del CrudRepository
    @Query("select p from Person p where p.programmingLenguage=?1")
    List<Person> buscarByProgrammingLenguage(String programmingLenguage);

    List<Person> findByProgrammingLenguageAndName(String programmingLenguage, String name);

    List<Person> findByName(String name);

    @Query("select p.name, p.lastname from Person p")
    List<Object[]> obtenerPersonaData();

    @Query("SELECT p FROM Person p WHERE p.name LIKE %:name%")
    Optional<Person> findOneLikeName(@Param("name") String name);

    Optional<Person> findByNameContaining(String name);

}