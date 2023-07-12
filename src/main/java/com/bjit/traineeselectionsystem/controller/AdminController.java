package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.service.AdminService;
import com.bjit.traineeselectionsystem.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }


    @PostMapping("/create/circular")
    public ResponseEntity<Response<?>> createCircular(@RequestBody CircularCreateRequest circularCreateRequest) {
        return adminService.createCircular(circularCreateRequest);
    }
    @GetMapping("/getAllCircular")
    public  ResponseEntity<Response<?>> getAllCircular(){
        return adminService.getAllCircular();
    }

    @PostMapping("/create/evaluator")
    public ResponseEntity<Object> createEvaluator(@RequestBody EvaluatorCreateRequest evaluatorCreateRequest) {
        return adminService.createEvaluator(evaluatorCreateRequest);
    }

    @GetMapping("/getAllEvaluator")
    public  ResponseEntity<Response<?>> getAllEvaluator(){
        return adminService.getAllEvaluator();
    }
}
