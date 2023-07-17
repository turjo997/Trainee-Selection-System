package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.service.ApproveService;
import com.bjit.traineeselectionsystem.service.ExamTrackService;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/approve")
@RequiredArgsConstructor

public class ApproveController {
    private final ServiceManager serviceManager;


    @PostMapping("/written/{adminId}/{applicantId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicantForWritten(@PathVariable Long adminId , @PathVariable Long applicantId , @PathVariable Long circularId , @PathVariable Long examId) {
        serviceManager.getApproveService().approveApplicant(adminId , applicantId, circularId , examId);
        return ResponseEntity.ok("Applicant approved successfully");
    }


    @PostMapping("/technical/{adminId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicantForTechnical(@PathVariable Long adminId , @PathVariable Long circularId , @PathVariable Long examId) {
        serviceManager.getExamTrackService().createExamTracks(adminId , circularId , examId);
        return ResponseEntity.ok("Applicant approved successfully");
    }
}
