package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.AuthenticationRequest;
import com.bjit.traineeselectionsystem.model.AuthenticationResponse;
import com.bjit.traineeselectionsystem.model.UploadMarksRequest;
import com.bjit.traineeselectionsystem.service.UploadMarksService;
import com.bjit.traineeselectionsystem.service.impl.AuthenticationService;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluator")
public class EvaluatorController {


    private final AuthenticationService authenticationService;
    private final ServiceManager serviceManager;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/upload-marks")
    public ResponseEntity<String> uploadMarks(@RequestBody UploadMarksRequest uploadMarksRequest) {
        return serviceManager.getUploadMarksService().uploadMarksByEvaluator(uploadMarksRequest);
    }


}
