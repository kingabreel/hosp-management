package org.proway.student.hospitalsys.controller;

import org.proway.student.hospitalsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/doctor")
    public ResponseEntity getDoctors(){
        return ResponseEntity.ok(userService.getDoctors());
    }

    @GetMapping("/receptionist")
    public ResponseEntity getReceptionists(){
        return ResponseEntity.ok(userService.getReceptionists());
    }

    @GetMapping("/patient")
    public ResponseEntity getPatients(){
        return ResponseEntity.ok(userService.getPatients());
    }
}
