package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.ApprovalModel;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin/approve")
@RequiredArgsConstructor

public class ApproveController {
    private final ServiceManager serviceManager;


    @PostMapping
    public ResponseEntity<String> approveApplicantForWritten(@RequestBody ApprovalModel approvalModel) {
        return serviceManager.getApproveService().approveApplicant(approvalModel);
    }


    @PostMapping("/exam/{userId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicant(@PathVariable Long userId , @PathVariable Long circularId , @PathVariable Long examId) {
        return serviceManager.getApproveService().selectTopApplicants(userId , circularId , examId);
    }

    @GetMapping("/get/{applicantId}/{circularId}")
    public boolean getApplicantsByApproval(@PathVariable Long applicantId , @PathVariable Long circularId){
        return serviceManager.getApproveService().getApproveByApplicantId(applicantId , circularId);
    }

}
