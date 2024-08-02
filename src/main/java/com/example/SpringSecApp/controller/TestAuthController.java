package com.example.SpringSecApp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class TestAuthController {

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('READ')")
    public String hellowrld(){
        return "Hello wrld";
    }


    //Authorizated method
    @PostMapping("/post")
    @PreAuthorize("hasAuthority('CREATE')")
    public String hellowrldAuth(){
        return "POST - Hello world";
    }





}
