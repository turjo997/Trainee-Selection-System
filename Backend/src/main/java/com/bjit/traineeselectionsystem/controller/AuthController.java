package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.AuthenticationRequest;
import com.bjit.traineeselectionsystem.model.AuthenticationResponse;
import com.bjit.traineeselectionsystem.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/login")
public class AuthController {
        private final AuthenticationService authenticationService;
        @PostMapping
        public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
        }
}
