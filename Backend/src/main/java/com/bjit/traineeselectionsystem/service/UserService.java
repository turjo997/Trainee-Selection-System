package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.ApplicantCreateModel;
import org.springframework.http.ResponseEntity;

public interface UserService {
    //ResponseEntity <Response<?>> addAdmin();
    ResponseEntity<Object> addAdmin();

    ResponseEntity<Object> addApplicant(ApplicantCreateModel applicantCreateRequest);
}
