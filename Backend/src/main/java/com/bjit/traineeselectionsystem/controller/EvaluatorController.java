package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.ApplicantUpdateRequest;
import com.bjit.traineeselectionsystem.model.EvaluatorUpdateRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksByEvaluatorRequest;
import com.bjit.traineeselectionsystem.service.impl.AuthenticationService;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluator")
public class EvaluatorController {


    private final AuthenticationService authenticationService;
    private final ServiceManager serviceManager;


    @PostMapping("/uploadMarks")
    public ResponseEntity<String> uploadMarks(@RequestBody UploadMarksByEvaluatorRequest uploadMarksByEvaluatorRequest) {
        return serviceManager.getUploadMarksService().uploadMarksByEvaluator(uploadMarksByEvaluatorRequest);
    }

    @GetMapping("/get/{applicantId}/{circularId}")
    public boolean isMarkUploaded(@PathVariable Long applicantId , @PathVariable Long circularId){
        return serviceManager.getApproveService().isMarksUploadedByApplicantId(applicantId , circularId);
    }


    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getEvaluatorById(@PathVariable Long userId) {
        return serviceManager.getEvaluatorService().getEvaluatorById(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody EvaluatorUpdateRequest evaluatorUpdateRequest){
        return serviceManager.getEvaluatorService().updateEvaluator(evaluatorUpdateRequest);
    }



}
