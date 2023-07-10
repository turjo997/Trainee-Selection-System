package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.ApplicantCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final UserService userService;
    @PostMapping("/register")
    public String register(
            @RequestParam("image") MultipartFile file1 ,
//            @RequestParam("cv") MultipartFile file2,
            @RequestBody ApplicantCreateRequest applicantCreateRequest)
            throws IOException {
        userService.addApplicant(file1, applicantCreateRequest);
        return "Applicant registered successfully";
    }
}
