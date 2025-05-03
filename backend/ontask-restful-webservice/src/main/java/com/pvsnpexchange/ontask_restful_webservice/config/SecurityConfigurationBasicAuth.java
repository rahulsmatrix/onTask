package com.pvsnpexchange.ontask_restful_webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationBasicAuth {

    private JWTFilter jwtFilter;

    public SecurityConfigurationBasicAuth(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests().anyRequest().permitAll();

        http
                .csrf((csrf) -> csrf.disable()
//                        .ignoringRequestMatchers("/no-csrf")
                )
                .cors((cors) -> cors.disable());

        http
                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/login","/api/login/user",
                                "/api/registeruser",
                                "/api/basicauth","/api/jwtauth")
                        .permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll()
                );
//                .httpBasic(withDefaults());

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

        return http.build();
    }

}
