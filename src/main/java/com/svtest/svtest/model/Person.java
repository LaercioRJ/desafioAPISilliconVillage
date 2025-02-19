package com.svtest.svtest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "people")
public class Person extends BaseModel {
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;*/

    @NotEmpty
    @Size(min = 3, max = 40)
    private String name;

    @NotEmpty
    @Size(min = 8, max=8)
    private String cpf;

    @Temporal(TemporalType.DATE)
    private java.sql.Date dateCreated;

    public Person() {}

    public Person(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public String getName() {
        return this.name;
    }
}
