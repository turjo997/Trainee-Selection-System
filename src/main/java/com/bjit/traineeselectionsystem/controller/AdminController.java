package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.model.UserCreateRequest;
import com.bjit.traineeselectionsystem.service.AdminService;
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

    private final AdminService adminService;

    @PostMapping("/create/circular")
    public ResponseEntity<Response<?>> createCircular(@RequestBody CircularCreateRequest circularCreateRequest) {
        return adminService.createCircular(circularCreateRequest);
    }

}
