package com.pvsnpexchange.ontask_restful_webservice.controller;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.payload.LoginDTO;
import com.pvsnpexchange.ontask_restful_webservice.payload.LoginResponse;
import com.pvsnpexchange.ontask_restful_webservice.service.AuthService;
import com.pvsnpexchange.ontask_restful_webservice.service.JWTService;
import com.pvsnpexchange.ontask_restful_webservice.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserRegistrationController {

    private UserRegistrationService userRegistrationService;
    private AuthService authService;
    private JWTService jwtService;

    @Autowired
    public UserRegistrationController(
            @Qualifier("userRegistrationService") UserRegistrationService userRegistrationService,
            @Qualifier("authService") AuthService authService,
            JWTService jwtService
    ) {
        this.userRegistrationService = userRegistrationService;
        this.authService = authService;
        this.jwtService = jwtService;
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

//    @GetMapping("/registeruser")
//    public List<User> findAll() {
//        return userRegistrationService.findAll();
//    }

    @PostMapping("/registeruser")
    public ResponseEntity<?> registerUser(@RequestBody User theUser) throws Exception {

        if(theUser != null) {
            String tempEmail = theUser.getEmail();
            if(tempEmail != null && !"".equals(tempEmail)) {
                User userObj = userRegistrationService.findByEmail(tempEmail);
                if(userObj != null) {
                    throw new Exception("User with email " + tempEmail + " already exist.");
                }
            }

            theUser.setId(0);
            String hashpw = BCrypt.hashpw(theUser.getPassword(), BCrypt.gensalt(12));
            theUser.setPassword(hashpw);
            userRegistrationService.save(theUser);
            return ResponseEntity.ok(theUser);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Bad request
                    .body("Invalid request");
        }

    }

//    @PostMapping("/login/user")
//    public User loginUser(@RequestBody User theUser) throws Exception {
//
//        String tempEmail = theUser.getEmail();
//        String tempPassword = theUser.getPassword();
//        User userObj = null;
//        if(tempEmail != null && tempPassword != null) {
//            userObj = userRegistrationService.findByEmailAndPassword(tempEmail, tempPassword);
//        }
//
//        if(userObj == null) {
//            throw new Exception("Bad Credentials");
//        }
//
//        return userObj;
//    }

    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO
                                       // HttpServletResponse response
    ) throws Exception {
        String jwtToken = authService.jwtAuthenticate(loginDTO);

        if(jwtToken != null) {
            // Set the JWT token in an HttpOnly cookie
            // jwtService.setJwtInCookie(response, jwtToken);

            User userObj = userRegistrationService.findByEmail(loginDTO.getEmail());

            // Return the user and token in a response entity
            // You might want to create a custom response object to include both user details and JWT
             LoginResponse loginResponse = new LoginResponse(userObj, jwtToken);

            return ResponseEntity.ok(loginResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                    .body("Invalid credentials");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> verifyLogin(@RequestBody LoginDTO loginDto) {
//        String jwtToken = authService.authenticate(loginDto);
//        if(jwtToken != null) {
//            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>("Invalid", HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable int userId) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        User tempUser = userRegistrationService.findById(userId);
        // Throw exception if null
        if(tempUser == null) {
            throw new RuntimeException("User with given id not found - " + userId);
        }
        userRegistrationService.deleteById(userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Deleted user with id - " + userId);

//        return ResponseEntity.status(HttpStatus.OK).body("Deleted user with id - " + userId);
        return ResponseEntity.ok(response);
    }

}
