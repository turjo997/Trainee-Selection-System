package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.model.EmailRequest;

import com.bjit.traineeselectionsystem.service.MailingStatusService;

import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import com.bjit.traineeselectionsystem.utils.ServiceManager;
import jakarta.mail.MessagingException;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmailSenderService implements MailingStatusService {

    private final JavaMailSender mailSender;
    private final RepositoryManager repositoryManager;

    @Override
    public void sendSimpleEmail(EmailRequest emailRequest) {


        AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(emailRequest.getAdminId())
                .orElseThrow(()-> new IllegalArgumentException("Admin not found"));

        JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(emailRequest.getCircularId())
                .orElseThrow(()-> new IllegalArgumentException("Circular not found"));


        ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(emailRequest.getExamId())
                .orElseThrow(()->new IllegalArgumentException("Exam not found"));


        // Get the approved applicants for a specific circular and exam
        List<ApproveEntity> approvedApplicants = repositoryManager.getApproveRepository().findByJobCircularCircularIdAndExamCategoryExamId(emailRequest.getCircularId(), emailRequest.getExamId());

        List<MailingStatusEntity> emailList = new ArrayList<>();


        for (ApproveEntity approve : approvedApplicants) {

            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findById(approve.getApplicant().getApplicantId())
                     .orElseThrow(()-> new IllegalArgumentException("Applicant not found"));

            if(approve.getApplicant().getApplicantId() == 12L){
                UserEntity userEntity = repositoryManager.getUserRepository().findById(applicantEntity.getUser().getUserId())
                        .orElseThrow(()->new IllegalArgumentException("User not found"));

                String toEmail = userEntity.getEmail();

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom("97.bhattacharjee.ullash@gmail.com");
                message.setTo(toEmail);
                message.setText(emailRequest.getBody());
                message.setSubject(emailRequest.getSubject());


                mailSender.send(message);


                MailingStatusEntity mailingStatusEntity = MailingStatusEntity.builder()
                        .admin(adminEntity)
                        .fromEmail("97.bhattacharjee.ullash@gmail.com")
                        .toEmail(toEmail)
                        .body(emailRequest.getBody())
                        .subject(emailRequest.getSubject())
                        .build();

                emailList.add(mailingStatusEntity);

                System.out.println("Mail Send...");
            }


        }

        repositoryManager.getMailingStatusRepository().saveAll(emailList);

    }

    @Override
    public void triggerMail(EmailRequest emailRequest) throws MessagingException {
        sendSimpleEmail(emailRequest);
    }

}
