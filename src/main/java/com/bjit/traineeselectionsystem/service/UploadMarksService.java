package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.UploadMarksHrRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksRequest;

public interface UploadMarksService {
    void uploadMarksByEvaluator(UploadMarksRequest uploadMarksRequest);
    void uploadMarksByAdmin(UploadMarksHrRequest uploadMarksHrRequest);
}
