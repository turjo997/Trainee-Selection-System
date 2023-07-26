package com.bjit.traineeselectionsystem.exception;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntryNotFoundException.class})
    public ResponseEntity<Response> EntryExceptionHandler(Exception ex) {
        Response<EntryNotFoundException> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ApplicantServiceException.class})
    public ResponseEntity<Response> ApplicantServiceExceptionHandler(Exception ex) {
        Response<ApplicantEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ApplyServiceException.class})
    public ResponseEntity<Response> ApplyServiceExceptionHandler(Exception ex) {
        Response<ApplyEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AdminServiceException.class})
    public ResponseEntity<Response> AdminServiceExceptionHandler(Exception ex) {
        Response<AdminEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({EvaluatorServiceException.class})
    public ResponseEntity<Response> EvaluatorServiceExceptionHandler(Exception ex) {
        Response<EvaluatorEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({JobCircularServiceException.class})
    public ResponseEntity<Response> JobServiceExceptionHandler(Exception ex) {
        Response<JobCircularEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserServiceException.class})
    public ResponseEntity<Response> UserServiceExceptionHandler(Exception ex) {
        Response<UserEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ExamCreateServiceException.class})
    public ResponseEntity<Response> ExamCreateServiceExceptionHandler(Exception ex) {
        Response<ExamCategoryEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({ApproveServiceException.class})
    public ResponseEntity<Response> ApproveExceptionHandler(Exception ex) {
        Response<ApproveEntity> apiResponse = new Response<>(null, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
