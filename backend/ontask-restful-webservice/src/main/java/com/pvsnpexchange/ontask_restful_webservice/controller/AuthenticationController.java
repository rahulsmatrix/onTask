package com.pvsnpexchange.ontask_restful_webservice.controller;

import com.pvsnpexchange.ontask_restful_webservice.payload.BasicAuthenticationBean;
import com.pvsnpexchange.ontask_restful_webservice.payload.JwtTokenResponse;
import com.pvsnpexchange.ontask_restful_webservice.payload.LoginDTO;
import com.pvsnpexchange.ontask_restful_webservice.service.AuthService;
import com.pvsnpexchange.ontask_restful_webservice.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private UserRegistrationService userRegistrationService;
    private AuthService authService;

    public AuthenticationController(UserRegistrationService userRegistrationService, AuthService authService) {
        this.userRegistrationService = userRegistrationService;
        this.authService = authService;
    }

    @GetMapping(path = "/basicauth")
    public BasicAuthenticationBean authenticateUser() {
        return new BasicAuthenticationBean("Authenticated user");
    }

//    @PostMapping("/jwtauth")
//    public ResponseEntity<?> jwtAuthenticateUser(@RequestBody LoginDTO loginDTO) {
//        String jwtToken = authService.jwtAuthenticate(loginDTO);
//        if(jwtToken != null) {
//            // Wrap the JWT token in a response object and return it as JSON
//            JwtTokenResponse tokenResponse = new JwtTokenResponse(jwtToken);
//            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
//        }
//        else {
//            // Return "Invalid" with HTTP 401 Unauthorized
//            return new ResponseEntity<>("Invalid", HttpStatus.UNAUTHORIZED);
//        }
//    }

    @PostMapping("/message")
    public String getMessage() {
        return "Hello";
    }

}
