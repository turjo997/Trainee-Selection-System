package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.service.AdminService;
import com.bjit.traineeselectionsystem.service.AdmitCardService;
import com.bjit.traineeselectionsystem.service.UploadMarksService;
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
    private final AdmitCardService admitCardService;
    private final UploadMarksService uploadMarksService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/create/circular")
    public ResponseEntity<String> createCircular(@RequestBody CircularCreateRequest circularCreateRequest) {
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


    @PostMapping("/upload-marks")
    public ResponseEntity<String> uploadMarks(@RequestBody UploadMarksHrRequest uploadMarksHrRequest) {
        uploadMarksService.uploadMarksByAdmin(uploadMarksHrRequest);
        return ResponseEntity.ok("Marks uploaded successfully");
    }


    @PostMapping("/generateAdmitCard")
    public String generateAdmitCard(@RequestBody AdmitCardRequest admitCardRequest){
        admitCardService.generateAdmitCards(admitCardRequest);
        return "Successfully generated";
    }

}
