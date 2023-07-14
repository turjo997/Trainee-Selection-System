package com.bjit.traineeselectionsystem.service;

public interface CodeGeneratorService {
     String writeQRCode(Long circularId, Long examId) throws Exception;
}
