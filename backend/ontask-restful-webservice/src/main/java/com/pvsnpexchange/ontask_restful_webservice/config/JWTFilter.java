package com.pvsnpexchange.ontask_restful_webservice.config;

import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.service.JWTService;
import com.pvsnpexchange.ontask_restful_webservice.service.UserRegistrationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserRegistrationService userRegistrationService;

    public JWTFilter(JWTService jwtService, UserRegistrationService userRegistrationService) {
        this.jwtService = jwtService;
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // If token is stored in 'HttpOnly Cookies' then, JWT Token Extraction:
        // ** The token is now extracted from the cookies (not the Authorization header).

        // To retrieve the token from cookies
        // String token = getTokenFromCookies(request);

        String token = request.getHeader("Authorization");
        // System.out.println(token);
        if(token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7,token.length());
            try {
                String userEmail = jwtService.getUsername(jwtToken);
                 // System.out.println(userEmail);
                 // System.out.println(jwtToken);

                User checkUser = userRegistrationService.findByEmail(userEmail);

                if (checkUser != null) {
                    User user = checkUser;

                    // Set user details in Spring Security
                    UsernamePasswordAuthenticationToken
                            authenticationToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            null
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.getWriter().write("Invalid or expired JWT token.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    // To get token from cookies.
    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
