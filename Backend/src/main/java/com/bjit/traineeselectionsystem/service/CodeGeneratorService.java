package com.bjit.traineeselectionsystem.service;

import org.springframework.http.ResponseEntity;

public interface CodeGeneratorService {
     ResponseEntity<String> writeQRCode(Long circularId) throws Exception;
}
