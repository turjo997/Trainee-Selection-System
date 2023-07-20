package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.ApplicantResponseModel;
import com.bjit.traineeselectionsystem.model.ApplyRequest;
import com.bjit.traineeselectionsystem.model.ApplicantUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicantService {

    ResponseEntity<String>  apply(ApplyRequest applyRequest);
    ResponseEntity<String> updateApplicant(ApplicantUpdateRequest applicantUpdateRequest);

    ResponseEntity<?> getAppliedApplicantsByCircularId(Long circularId);
}
