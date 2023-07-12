package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.ApplicantCreateRequest;
import com.bjit.traineeselectionsystem.model.ApplyRequest;
import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.service.ApplicantService;
import com.bjit.traineeselectionsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final UserService userService;
    private final ApplicantService applicantService;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody ApplicantCreateRequest applicantCreateRequest) {

        return userService.addApplicant(applicantCreateRequest);
        //return "Applicant registered successfully";
    }


    @PostMapping("/apply")
    public ResponseEntity<Response<?>> apply(@RequestBody ApplyRequest applyRequest) {
        return applicantService.apply(applyRequest);
    }
}
