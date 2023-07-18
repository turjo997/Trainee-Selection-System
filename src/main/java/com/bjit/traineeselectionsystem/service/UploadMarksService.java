package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.UploadMarksHrRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksRequest;
import org.springframework.http.ResponseEntity;

public interface UploadMarksService {
    ResponseEntity<String> uploadMarksByEvaluator(UploadMarksRequest uploadMarksRequest);
    ResponseEntity<String> uploadMarksByAdmin(UploadMarksHrRequest uploadMarksHrRequest);
}
