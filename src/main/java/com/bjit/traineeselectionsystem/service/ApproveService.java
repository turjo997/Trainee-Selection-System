package com.bjit.traineeselectionsystem.service;

import org.springframework.http.ResponseEntity;

public interface ApproveService {
    ResponseEntity<String> approveApplicant(Long adminId , Long applicantId , Long circularId , Long examId);

    void selectTopApplicants(Long adminId ,Long circularId , Long examId);
}
