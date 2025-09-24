package com.sample.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sample.entity.Person;
import com.sample.repository.PersonRepository;
import com.sample.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	private final PersonRepository repository;

	@Override
	public Person save(Person person) {
		return repository.save(person);
	}

	@Override
	public List<Person> findAll() {
		return repository.findAll(Pageable.unpaged()).getContent();
	}

}
