package com.sample.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "person")
public class Person {

	@Id // like @Id in JPA
	private String id;

	private String name;
	private int age;
}
