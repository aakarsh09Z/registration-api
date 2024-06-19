package com.aakarsh09z.registrationapi.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    This class contains the endpoint which tests whether the application is running and checking the endpoints
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class GeneralController {
    @GetMapping("")
    public String test(){
        return "Application is running!!";
    }
}
