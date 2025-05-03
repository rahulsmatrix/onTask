package com.pvsnpexchange.ontask_restful_webservice.entity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class Cars {

    @PostMapping
    public String addCars() {
        return "added";
    }
}
