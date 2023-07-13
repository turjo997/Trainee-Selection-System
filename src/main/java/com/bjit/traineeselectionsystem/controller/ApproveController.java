package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.service.ApproveService;
import com.bjit.traineeselectionsystem.service.ExamTrackService;
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
    private final ApproveService approveService;
    private final ExamTrackService examTrackService;

    @PostMapping("/written/{adminId}/{applicantId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicantForWritten(@PathVariable Long adminId , @PathVariable Long applicantId , @PathVariable Long circularId , @PathVariable Long examId) {
        approveService.approveApplicant(adminId , applicantId, circularId , examId);
        return ResponseEntity.ok("Applicant approved successfully");
    }


    @PostMapping("/technical/{adminId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicantForTechnical(@PathVariable Long adminId , @PathVariable Long circularId , @PathVariable Long examId) {
        examTrackService.createExamTracks(adminId , circularId , examId);
        return ResponseEntity.ok("Applicant approved successfully");
    }
}
