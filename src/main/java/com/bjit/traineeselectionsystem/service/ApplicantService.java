package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.ApplyRequest;
import com.bjit.traineeselectionsystem.model.Response;
import org.springframework.http.ResponseEntity;

public interface ApplicantService {

    ResponseEntity<Response<?>>  apply(ApplyRequest applyRequest);
}
