package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.model.EvaluatorCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.model.UserCreateRequest;
import com.bjit.traineeselectionsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create/circular")
    public ResponseEntity<Response<?>> createCircular(@RequestBody CircularCreateRequest circularCreateRequest) {
        return adminService.createCircular(circularCreateRequest);
    }
    @GetMapping("/getAllCircular")
    public  ResponseEntity<Response<?>> getAllCircular(){
        return adminService.getAllCircular();
    }

    @PostMapping("/create/evaluator")
    public ResponseEntity<Response<?>> createEvaluator(@RequestBody EvaluatorCreateRequest evaluatorCreateRequest) {
        return adminService.createEvaluator(evaluatorCreateRequest);
    }

    @GetMapping("/getAllEvaluator")
    public  ResponseEntity<Response<?>> getAllEvaluator(){
        return adminService.getAllEvaluator();
    }
}
