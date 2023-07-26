package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.NoticeModel;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class NoticeBoardController {

    private final ServiceManager serviceManager;

    @PostMapping("/send/notice")
    public ResponseEntity<String> sendNotice(@RequestBody NoticeModel noticeModel){
        return serviceManager.getAdminService().sendNotice(noticeModel);
    }
}
