package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.service.impl.AuthenticationService;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ServiceManager serviceManager;
    private final AuthenticationService authenticationService;


    @PostMapping("/create/circular")
    public ResponseEntity<String> createCircular(@RequestBody CircularCreateRequest circularCreateRequest) {
        return serviceManager.getAdminService().createCircular(circularCreateRequest);
    }
    @GetMapping("/getAllCircular")
    public  ResponseEntity<Response<?>> getAllCircular(){
        return serviceManager.getAdminService().getAllCircular();
    }

    @PostMapping("/create/evaluator")
    public ResponseEntity<Object> createEvaluator(@RequestBody EvaluatorModel evaluatorModel) {
        return serviceManager.getAdminService().createEvaluator(evaluatorModel);
    }

    @GetMapping("/getAllEvaluator")
    public  ResponseEntity<Response<?>> getAllEvaluator(){
        return serviceManager.getAdminService().getAllEvaluator();
    }


    @PostMapping("/uploadMarks")
    public ResponseEntity<String> uploadMarks(@RequestBody UploadMarksByAdminRequest uploadMarksByAdminRequest) {
        return serviceManager.getUploadMarksService().uploadMarksByAdmin(uploadMarksByAdminRequest);
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


    @GetMapping("/getApplicants/{circularId}/{examId}")
    public ResponseEntity<?> getApplicantsByTechnicalAndHr(@PathVariable Long circularId , @PathVariable Long examId){
        return serviceManager.getApplicantService().getApplicantsByTechnicalAndHr(circularId ,  examId);
    }


    @GetMapping("/getApplicants/written/{circularId}")
    public ResponseEntity<?> getApprovedApplicantsForWrittenTest(@PathVariable Long circularId){
        return serviceManager.getApplicantService().getApprovedApplicantsForWrittenTest(circularId);
    }

    @GetMapping("/get/{applicantId}/{circularId}/{examId}")
    public boolean isMarkUploaded
            (@PathVariable Long applicantId , @PathVariable Long circularId , @PathVariable Long examId) {
        return serviceManager.getAdminService()
                .isMarksUploadedByApplicantId(applicantId , circularId , examId);
    }


    @GetMapping("/generate/{circularId}")
    public ResponseEntity<String>generateQRCode(@PathVariable Long circularId) throws Exception {
        return serviceManager.getCodeGeneratorService().writeQRCode(circularId);
    }


    @PostMapping("/get/trainees/{circularId}")
    public  ResponseEntity<Response<?>> getFinalTrainees(@PathVariable Long circularId){
        return serviceManager.getAdminService().getTrainees(circularId);
    }


}
