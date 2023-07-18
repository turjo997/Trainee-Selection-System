package com.bjit.traineeselectionsystem.controller;


import com.bjit.traineeselectionsystem.model.EmailRequest;
import com.bjit.traineeselectionsystem.service.impl.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class EmailController {

    private final EmailSenderService emailSenderService;
    @PostMapping("/sendMail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailSenderService.triggerMail(emailRequest);
        return "Successfully send email";
    }
}
