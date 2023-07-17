package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.ExamCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
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
public class ExamController {

    private final AdminService adminService;
    @PostMapping("/create/examCategory")
    public ResponseEntity<String> createExam(@RequestBody ExamCreateRequest examCreateRequest) {
        return adminService.createExamCategory(examCreateRequest);
    }
}
