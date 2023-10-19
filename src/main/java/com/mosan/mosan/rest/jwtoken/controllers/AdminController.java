package com.mosan.mosan.rest.jwtoken.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/")
    public ResponseEntity<String>getAllUsersRecords(){
        return ResponseEntity.status(200).body("Okay");
    }
}
