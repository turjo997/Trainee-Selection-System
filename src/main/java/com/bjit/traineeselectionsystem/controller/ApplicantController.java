package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.model.ApplicantUpdateRequest;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final ServiceManager serviceManager;


    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody ApplicantCreateRequest applicantCreateRequest) {

        return serviceManager.getUserService().addApplicant(applicantCreateRequest);
        //return "Applicant registered successfully";
    }


    @PutMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody ApplicantUpdateRequest applicantUpdateRequest){
        return serviceManager.getApplicantService().updateApplicant(applicantUpdateRequest);
    }

    @PostMapping("/apply")
    public ResponseEntity<String> apply(@RequestBody ApplyRequest applyRequest) {
        return serviceManager.getApplicantService().apply(applyRequest);
    }
}
