package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/approve")
public class ExamTrackController {

    private final ServiceManager serviceManager;

    @PostMapping("/technical/{adminId}/{circularId}/{examId}")
    public ResponseEntity<String> approveApplicantForTechnical(@PathVariable Long adminId , @PathVariable Long circularId , @PathVariable Long examId) {
        return serviceManager.getExamTrackService().createExamTracks(adminId , circularId , examId);
    }

}
