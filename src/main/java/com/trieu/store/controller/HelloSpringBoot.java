package com.trieu.store.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// test thử
@RestController
public class HelloSpringBoot {
    @GetMapping("/hello")
    String  sayHello(){
        return "Hello Spring boot";
    }
}
