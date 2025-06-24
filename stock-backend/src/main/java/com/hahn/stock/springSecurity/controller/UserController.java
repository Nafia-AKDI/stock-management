package com.hahn.stock.springSecurity.controller;


import com.hahn.stock.dto.UserDTO;
import com.hahn.stock.springSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users/save")
    public ResponseEntity<Object> saveUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO result = userService.saveUser(userDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO result = userService.updateUser(userDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/auth/check-token-expiration")
    public ResponseEntity<Boolean> checkTokenExpiration(@RequestParam("token") String token) {
        return userService.checkTokenExpiration(token);
    }

//    @PostMapping("/gentoken")
//    public void genToken() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = "nafie123";
//        String hashedPassword = encoder.encode(rawPassword);
//        log.error("hashed password : " + hashedPassword);
//    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> getUser(@RequestHeader("Authorization") String authorization) {
        try {
            UserDTO userDTO = userService.getUser(authorization);
            return ResponseEntity.ok().body(userDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


}
