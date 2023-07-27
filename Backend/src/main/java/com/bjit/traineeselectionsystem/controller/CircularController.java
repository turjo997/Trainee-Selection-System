package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/get")
public class CircularController {

    private final ServiceManager serviceManager;

    @GetMapping("/{circularId}")
    public ResponseEntity<?> getCircularById(@PathVariable Long circularId) {
        return serviceManager.getAdminService().getCircularById(circularId);
    }

}
