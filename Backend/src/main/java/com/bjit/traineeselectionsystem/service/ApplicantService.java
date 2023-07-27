package com.bjit.traineeselectionsystem.service;


import com.bjit.traineeselectionsystem.model.ApplyRequest;
import com.bjit.traineeselectionsystem.model.ApplicantUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicantService {

    ResponseEntity<String>  apply(ApplyRequest applyRequest);
    ResponseEntity<String> updateApplicant(ApplicantUpdateRequest applicantUpdateRequest);

    ResponseEntity<?> getAppliedApplicantsByCircularId(Long circularId);

    ResponseEntity<?> getApplicantsByTechnicalAndHr(Long circularId , Long examId);


    ResponseEntity<?> getApplicantById(Long userId);


    ResponseEntity<?> getNotificationsForApplicant(Long userId);

    boolean isApplied(Long circularId , Long userId);


    ResponseEntity<?> getApprovedApplicantsForWrittenTest(Long circularId);


}
