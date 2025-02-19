package com.svtest.svtest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svtest.svtest.model.Person;
import com.svtest.svtest.repository.PeopleRepository;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public Optional<Person> getById(Long id) {
        return this.peopleRepository.findById(id);
    }
}
