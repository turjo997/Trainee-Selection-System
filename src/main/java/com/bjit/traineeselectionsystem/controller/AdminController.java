package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.service.impl.AuthenticationService;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ServiceManager serviceManager;
    private final AuthenticationService authenticationService;

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
//        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
//    }

    @PostMapping("/create/circular")
    public ResponseEntity<String> createCircular(@RequestBody CircularCreateRequest circularCreateRequest) {
        return serviceManager.getAdminService().createCircular(circularCreateRequest);
    }
    @GetMapping("/getAllCircular")
    public  ResponseEntity<Response<?>> getAllCircular(){
        return serviceManager.getAdminService().getAllCircular();
    }

    @PostMapping("/create/evaluator")
    public ResponseEntity<Object> createEvaluator(@RequestBody EvaluatorCreateRequest evaluatorCreateRequest) {
        return serviceManager.getAdminService().createEvaluator(evaluatorCreateRequest);
    }

    @GetMapping("/getAllEvaluator")
    public  ResponseEntity<Response<?>> getAllEvaluator(){
        return serviceManager.getAdminService().getAllEvaluator();
    }


    @PostMapping("/upload-marks")
    public ResponseEntity<String> uploadMarks(@RequestBody UploadMarksHrRequest uploadMarksHrRequest) {
        return serviceManager.getUploadMarksService().uploadMarksByAdmin(uploadMarksHrRequest);
    }


    @PostMapping("/generateAdmitCard")
    public ResponseEntity<String> generateAdmitCard(@RequestBody AdmitCardRequest admitCardRequest){
        return serviceManager.getAdmitCardService().generateAdmitCards(admitCardRequest);
    }

    @GetMapping("/getAllApplicant")
    public  ResponseEntity<Response<?>> getAllApplicant(){
        return serviceManager.getAdminService().getAllApplicant();
    }

    @GetMapping("/getAllExamCategory")
    public  ResponseEntity<Response<?>> getAllExamCategory(){
        return serviceManager.getAdminService().getAllExamCategory();
    }

    @GetMapping("/getApplicants/{circularId}")
    public ResponseEntity<?> getApplicantsByCircular(@PathVariable Long circularId){
        return serviceManager.getApplicantService().getAppliedApplicantsByCircularId(circularId);
    }



}
