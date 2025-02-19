package com.svtest.svtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svtest.svtest.model.Person;

public interface PeopleRepository extends JpaRepository<Person, Long> {
    
}
