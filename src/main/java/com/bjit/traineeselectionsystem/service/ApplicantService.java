package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.ApplyRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.model.ApplicantUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface ApplicantService {

    ResponseEntity<String>  apply(ApplyRequest applyRequest);
    ResponseEntity<Response<?>> updateApplicant(ApplicantUpdateRequest applicantUpdateRequest);
}
