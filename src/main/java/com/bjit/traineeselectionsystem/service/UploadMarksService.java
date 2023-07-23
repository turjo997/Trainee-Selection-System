package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.UploadMarksByAdminRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksByEvaluatorRequest;
import org.springframework.http.ResponseEntity;

public interface UploadMarksService {
    ResponseEntity<String> uploadMarksByEvaluator(UploadMarksByEvaluatorRequest uploadMarksByEvaluatorRequest);
    ResponseEntity<String> uploadMarksByAdmin(UploadMarksByAdminRequest uploadMarksByAdminRequest);
}
