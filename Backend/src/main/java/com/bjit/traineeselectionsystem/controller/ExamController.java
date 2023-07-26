package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.ExamCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.service.AdminService;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ExamController {

    private final ServiceManager serviceManager;
    @PostMapping("/create/examCategory")
    public ResponseEntity<String> createExam(@RequestBody ExamCreateRequest examCreateRequest) {
        return serviceManager.getAdminService().createExamCategory(examCreateRequest);
    }
}
