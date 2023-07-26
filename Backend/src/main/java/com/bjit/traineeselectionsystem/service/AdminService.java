package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.*;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<String> createCircular(CircularCreateRequest circularCreateRequest);
    ResponseEntity<Response<?>> getAllCircular();

    ResponseEntity<Object> createEvaluator(EvaluatorModel evaluatorModel);
    ResponseEntity<Response<?>> getAllEvaluator();

    ResponseEntity<String> createExamCategory(ExamCreateRequest examCreateRequest);

    ResponseEntity<Response<?>> getAllApplicant();

    ResponseEntity<Response<?>> getAllExamCategory();

    ResponseEntity<?> getCircularById(Long circularId);

    ResponseEntity<String> sendNotice(NoticeModel noticeModel);


    boolean isMarksUploadedByApplicantId(Long applicantId, Long circularId , Long examId);

    ResponseEntity<Response<?>> getTrainees( Long circularId);

}
