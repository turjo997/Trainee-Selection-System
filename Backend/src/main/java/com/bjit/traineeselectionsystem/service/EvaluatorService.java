package com.bjit.traineeselectionsystem.service;


import com.bjit.traineeselectionsystem.model.EvaluatorUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface EvaluatorService {

    ResponseEntity<?> getEvaluatorById(Long userId);

    ResponseEntity<String> updateEvaluator(EvaluatorUpdateRequest evaluatorUpdateRequest);
}
