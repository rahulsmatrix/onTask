package com.pvsnpexchange.ontask_restful_webservice.controller;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/welcome/user")
public class WelcomeController {

    private UserRegistrationService userRegistrationService;

    @Autowired
    public WelcomeController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    private int getAuthenticatedUserId() {
        // Retrieve the authenticated user from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User authenticatedUser = (User) authentication.getPrincipal();
            return authenticatedUser.getId(); // Get the authenticated user's ID
        }
        throw new RuntimeException("User not authenticated");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, String>> welcomeUserById(@PathVariable int userId) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        User tempUser = userRegistrationService.findById(userId);

        String userName =  tempUser.getFirstName();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome! Have a good day, " + userName);

        return ResponseEntity.ok(response);
    }

}
