package com.bjit.traineeselectionsystem.controller;

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


//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
//        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
//    }

    @PostMapping("/uploadMarks")
    public ResponseEntity<String> uploadMarks(@RequestBody UploadMarksByEvaluatorRequest uploadMarksByEvaluatorRequest) {
        return serviceManager.getUploadMarksService().uploadMarksByEvaluator(uploadMarksByEvaluatorRequest);
    }


    @GetMapping("/get/{applicantId}/{circularId}")
    public boolean isMarkUploaded(@PathVariable Long applicantId , @PathVariable Long circularId){
        return serviceManager.getApproveService().isMarksUploadedByApplicantId(applicantId , circularId);
    }

}
