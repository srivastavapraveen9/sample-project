package com.sample.service;

import java.util.List;

import com.sample.entity.Person;

public interface PersonService {

	Person save(Person person);

	List<Person> findAll();

}
