package com.pvsnpexchange.ontask_restful_webservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry-time}")
    private int expiry;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(String email) {
        // Here username will be user email,
        // which will be passed whenever generateToken() method is called.
        return JWT.create()
                .withClaim("name", email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiry))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    // It’s recommended to store the JWT token in HttpOnly cookies.
    // To enhance security and prevent logging the JWT token in the browser's local storage, session storage, or console.
    // HttpOnly cookies can only be set by the server. HttpOnly cookies are secure because they cannot be accessed via JavaScript,
    // which helps protect the token from potential XSS attacks.

    // Set JWT Token in HttpOnly Cookie for better security than sessionStorage
    // *#* NOT ENABLED or USED
    public void setJwtInCookie(HttpServletResponse response, String jwtToken) {
        Cookie cookie = new Cookie("authToken", jwtToken);
        cookie.setHttpOnly(true); // Prevent client-side access to the cookie via JavaScript
        cookie.setSecure(true); // Ensure the cookie is sent only over HTTPS (in production)
        cookie.setPath("/"); // The cookie is accessible on all paths
        cookie.setMaxAge(3600); // Set expiration time to 1 hour (you can modify this)
        response.addCookie(cookie);
    }

    //Username will be user email here.
    public String getUsername(String token) {

        DecodedJWT decodedToken = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return decodedToken.getClaim("name").asString();

    }

}
