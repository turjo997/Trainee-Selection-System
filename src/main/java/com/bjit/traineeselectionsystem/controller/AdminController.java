package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.model.UserCreateRequest;
import com.bjit.traineeselectionsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
//    private final UserService userService;
//
//    @PostMapping("/create")
//    public ResponseEntity<Response<?>> createAdmin(@RequestBody UserCreateRequest userCreateRequest) {
//        return userService.addAdmin(userCreateRequest);
//    }

}
