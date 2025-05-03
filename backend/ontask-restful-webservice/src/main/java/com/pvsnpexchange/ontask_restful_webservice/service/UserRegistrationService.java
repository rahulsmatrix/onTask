package com.pvsnpexchange.ontask_restful_webservice.service;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;

import java.util.Optional;

public interface UserRegistrationService {

//    public List<User> findAll();

    public User findById(int userId);

    public User findByEmail(String email);

    public User findByEmailAndPassword(String email, String password);

    public void save(User theUser);

    public void deleteById(int userId);

}
