package com.journalApp.controller;

import com.journalApp.entity.User;
import com.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveNewEntry(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user, String userName) {
//        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if(userInDb == null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
//package com.journalApp.controller;
//
//import com.journalApp.entity.User;
//import com.journalApp.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//
//    // CREATE USER
//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//
//        userService.saveNewEntry(user);
//
//        return new ResponseEntity<>(user, HttpStatus.CREATED);
//    }
//
//
//    // GET ALL USERS
//    @GetMapping("/all")
//    public ResponseEntity<?> getAllUsers() {
//
//        List<User> users = userService.getAll();
//
//        if(users != null && !users.isEmpty()) {
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//
//    // GET CURRENT LOGGED-IN USER
//    @GetMapping
//    public ResponseEntity<?> getUser() {
//
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        String userName = authentication.getName();
//
//        User user = userService.findByUserName(userName);
//
//        if(user != null) {
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//
//    // UPDATE USER
//    @PutMapping
//    public ResponseEntity<?> updateUser(@RequestBody User user) {
//
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        String userName = authentication.getName();
//
//        User userInDb = userService.findByUserName(userName);
//
//
//        if(userInDb != null) {
//
//            userInDb.setUserName(user.getUserName());
//            userInDb.setPassword(user.getPassword());
//
//            userService.saveEntry(userInDb);
//
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//
//
//    // DELETE USER
//    @DeleteMapping
//    public ResponseEntity<?> deleteUser() {
//
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        String userName = authentication.getName();
//
//        userService.deleteByUserName(userName);
//
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}