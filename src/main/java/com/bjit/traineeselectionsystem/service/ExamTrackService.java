package com.bjit.traineeselectionsystem.service;


import org.springframework.http.ResponseEntity;

public interface ExamTrackService {
    ResponseEntity<String> createExamTracks(Long adminId, Long circularId, Long examId);
}