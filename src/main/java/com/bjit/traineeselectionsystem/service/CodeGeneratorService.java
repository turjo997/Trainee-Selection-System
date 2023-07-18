package com.bjit.traineeselectionsystem.service;

import org.springframework.http.ResponseEntity;

public interface CodeGeneratorService {
     ResponseEntity<String> writeQRCode(Long circularId, Long examId) throws Exception;
}
