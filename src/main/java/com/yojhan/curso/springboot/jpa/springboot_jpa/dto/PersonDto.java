package com.yojhan.curso.springboot.jpa.springboot_jpa.dto;

public class PersonDto {
    private String name;
    private String lastname;

    public PersonDto(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "PersonDto [name=" + name + ", lastname=" + lastname + "]";
    }

}
