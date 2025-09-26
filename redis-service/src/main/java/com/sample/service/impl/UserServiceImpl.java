package com.sample.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sample.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Cacheable(value = "users", key = "#id")
    public String getUserById(String id) {
        System.out.println("Fetching user from DB for id: " + id);
        return "User-" + id;
    }

    @CachePut(value = "users", key = "#id")
    public String updateUser(String id, String name) {
        System.out.println("Updating user in DB for id: " + id);
        return name;
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) {
        System.out.println("Deleting user from cache & DB for id: " + id);
    }
}
