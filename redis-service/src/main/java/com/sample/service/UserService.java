package com.sample.service;

public interface UserService {

	String getUserById(String id);

	String updateUser(String id, String name);

	void deleteUser(String id);

}
