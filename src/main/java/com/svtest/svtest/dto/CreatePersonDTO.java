package com.svtest.svtest.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class CreatePersonDTO {
    @NotNull(message = "The user needs a name to be registered!")
    String name;

    @NotNull(message = "The user needs a cpf to be registered!")
    String cpf;

    @NotNull(message = "Please, provide a birth date!")
    LocalDate birthdate;

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }
}
