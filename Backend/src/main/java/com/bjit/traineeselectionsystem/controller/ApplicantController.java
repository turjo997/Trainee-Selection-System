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
    public ResponseEntity<Object> register(@RequestBody ApplicantCreateModel applicantCreateRequest) {

        return serviceManager.getUserService().addApplicant(applicantCreateRequest);
        //return "Applicant registered successfully";
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody ApplicantUpdateRequest applicantUpdateRequest){
        return serviceManager.getApplicantService().updateApplicant(applicantUpdateRequest);
    }

    @PostMapping("/apply")
    public ResponseEntity<String> apply(@RequestBody ApplyRequest applyRequest) {
        return serviceManager.getApplicantService().apply(applyRequest);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getApplicantById(@PathVariable Long userId) {
        return serviceManager.getApplicantService().getApplicantById(userId);
    }


    @GetMapping("/notifications/{userId}")
    public ResponseEntity<?> getNotificationsForApplicant(@PathVariable Long userId) {
        return serviceManager.getApplicantService().getNotificationsForApplicant(userId);
    }


    @GetMapping("/get/{circularId}/{userId}")
    public boolean isAppliedForJob(@PathVariable Long circularId , @PathVariable Long userId) {
        return serviceManager.getApplicantService().isApplied(circularId , userId);
    }

}
