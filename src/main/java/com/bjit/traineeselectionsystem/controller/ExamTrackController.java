package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.model.ExamTrackModel;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ExamTrackController {

    private final ServiceManager serviceManager;

    @PostMapping("/track")
    public ResponseEntity<String> trackingExam(@RequestBody  ExamTrackModel examTrackModel) {
        return serviceManager.getExamTrackService().createExamTracks(examTrackModel);
    }

}
