package com.journalApp.controller;

import com.journalApp.entity.User;
import com.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {

        return "Ok";
    }
    @PostMapping("create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.saveNewEntry(user);
        return ResponseEntity.ok("User created");
    }
}

