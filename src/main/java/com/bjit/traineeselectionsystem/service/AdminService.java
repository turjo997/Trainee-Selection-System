package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<Response<?>> createCircular(CircularCreateRequest circularCreateRequest);
}
