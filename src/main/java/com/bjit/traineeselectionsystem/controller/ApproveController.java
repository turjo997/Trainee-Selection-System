package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.service.ApproveService;
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

    @PostMapping("/{adminId}/{applicantId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicant(@PathVariable Long adminId , @PathVariable Long applicantId , @PathVariable Long circularId , @PathVariable Long examId) {
        approveService.approveApplicant(adminId , applicantId, circularId , examId);
        return ResponseEntity.ok("Applicant approved successfully");
    }
}
