package com.sample.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sample.entity.Person;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

}
