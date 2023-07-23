package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.service.ApproveService;
import com.bjit.traineeselectionsystem.service.ExamTrackService;
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


    @PostMapping("/written/{adminId}/{applicantId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicantForWritten(@PathVariable Long adminId , @PathVariable Long applicantId , @PathVariable Long circularId , @PathVariable Long examId) {
        return serviceManager.getApproveService().approveApplicant(adminId , applicantId, circularId , examId);
    }


    @PostMapping("/{adminId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicant(@PathVariable Long adminId , @PathVariable Long circularId , @PathVariable Long examId) {
        return serviceManager.getApproveService().selectTopApplicants(adminId , circularId , examId);
    }


}
