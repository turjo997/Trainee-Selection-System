package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.EmailRequest;
import jakarta.mail.MessagingException;

public interface MailingStatusService {
    void sendSimpleEmail(EmailRequest emailRequest);

    void triggerMail(EmailRequest emailRequest) throws MessagingException;
}
