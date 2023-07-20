package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CircularController {

    private final ServiceManager serviceManager;

    @GetMapping("/getCircularById/{circularId}")
    public ResponseEntity<Response<?>> getCircularById(@PathVariable Long circularId) {
        return serviceManager.getAdminService().getCircularById(circularId);
    }

}
