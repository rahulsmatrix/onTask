package com.pvsnpexchange.ontask_restful_webservice.repository;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRegistrationRepository")
public interface UserRegistrationRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

    public User findByEmailAndPassword(String email, String password);

}
