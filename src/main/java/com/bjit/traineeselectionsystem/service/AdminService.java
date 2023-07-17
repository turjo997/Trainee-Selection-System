package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.model.EvaluatorCreateRequest;
import com.bjit.traineeselectionsystem.model.ExamCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<String> createCircular(CircularCreateRequest circularCreateRequest);
    ResponseEntity<Response<?>> getAllCircular();

    ResponseEntity<Object> createEvaluator(EvaluatorCreateRequest evaluatorCreateRequest);
    ResponseEntity<Response<?>> getAllEvaluator();

    ResponseEntity<String> createExamCategory(ExamCreateRequest examCreateRequest);
}
