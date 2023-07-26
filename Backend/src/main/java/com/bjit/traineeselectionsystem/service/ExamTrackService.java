package com.bjit.traineeselectionsystem.service;


import com.bjit.traineeselectionsystem.model.ExamTrackModel;
import org.springframework.http.ResponseEntity;

public interface ExamTrackService {
    ResponseEntity<String> createExamTracks(ExamTrackModel examTrackModel);
}