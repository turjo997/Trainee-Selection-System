package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.ApplicantCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.model.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    //ResponseEntity <Response<?>> addAdmin();
    void addAdmin();

    ResponseEntity<Object> addApplicant(ApplicantCreateRequest applicantCreateRequest);
}
