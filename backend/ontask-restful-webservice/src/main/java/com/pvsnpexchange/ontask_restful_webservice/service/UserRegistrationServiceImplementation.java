package com.pvsnpexchange.ontask_restful_webservice.service;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userRegistrationService")
public class UserRegistrationServiceImplementation implements UserRegistrationService {

    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    public UserRegistrationServiceImplementation(@Qualifier("userRegistrationRepository") UserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

//    @Override
//    public List<User> findAll() {
//        return userRegistrationRepository.findAll();
//    }

    @Override
    public User findById(int userId) {
        Optional<User> result = userRegistrationRepository.findById(userId);

        User theUser = null;

        if (result.isPresent()) {
            theUser =result.get();
        }
        else {
            // We did not find the employee
            throw new RuntimeException("Did not find user with id - " + userId);
        }
        return theUser;
    }

    @Override
    public void save(User theUser) {
        userRegistrationRepository.save(theUser);
    }

    public User findByEmail(String email) {
        return userRegistrationRepository.findByEmail(email);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRegistrationRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public void deleteById(int userId) {
        userRegistrationRepository.deleteById(userId);
    }

}
