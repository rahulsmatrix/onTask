package com.pvsnpexchange.ontask_restful_webservice.service;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.payload.LoginDTO;
import com.pvsnpexchange.ontask_restful_webservice.repository.UserRegistrationRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRegistrationRepository userRegistrationRepository;
    private JWTService jwtService;

    public AuthService(UserRegistrationRepository userRegistrationRepository, JWTService jwtService) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.jwtService = jwtService;
    }

    public boolean authenticate(LoginDTO loginDTO) {
       User checkUser = userRegistrationRepository.findByEmail(loginDTO.getEmail());

       if(checkUser != null) {
//          User user = checkUser;
          boolean status = BCrypt.checkpw(loginDTO.getPassword(), checkUser.getPassword());
          return status;
       }
       return false;
    }

    public String jwtAuthenticate(LoginDTO loginDTO) {
        User checkUser = userRegistrationRepository.findByEmail(loginDTO.getEmail());

        if(checkUser != null && BCrypt.checkpw(loginDTO.getPassword(), checkUser.getPassword())) {

//            User user = checkUser;
//            boolean status = BCrypt.checkpw(loginDTO.getPassword(), user.getPassword());
//            if(status) {
//                return jwtService.generateToken(user.getEmail());
//            }

            return jwtService.generateToken(checkUser.getEmail());
        }
        return null;
    }
}
