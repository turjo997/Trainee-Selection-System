package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
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

        try {
            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(emailRequest.getAdminId())
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(emailRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));


            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(emailRequest.getExamId())
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            // Get the approved applicants for a specific circular and exam
            List<ApproveEntity> approvedApplicants = repositoryManager.getApproveRepository().findByJobCircularCircularIdAndExamCategoryExamId(emailRequest.getCircularId(), emailRequest.getExamId());

            List<MailingStatusEntity> emailList = new ArrayList<>();


            for (ApproveEntity approve : approvedApplicants) {

                ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findById(approve.getApplicant().getApplicantId())
                        .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                if (approve.getApplicant().getApplicantId() == 12L) {
                    UserEntity userEntity = repositoryManager.getUserRepository().findById(applicantEntity.getUser().getUserId())
                            .orElseThrow(() -> new UserServiceException("User not found"));

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
        } catch (AdminServiceException e) {
            throw new AdminServiceException(e.getMessage());
        } catch (JobCircularServiceException e) {
            throw new JobCircularServiceException(e.getMessage());
        } catch (ExamCreateServiceException e) {
            throw new ExamCreateServiceException(e.getMessage());
        } catch (ApplicantServiceException e) {
            throw new ApplicantServiceException(e.getMessage());
        } catch (UserServiceException e) {
            throw new UserServiceException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }


    }

    @Override
    public void triggerMail(EmailRequest emailRequest) throws MessagingException {
        sendSimpleEmail(emailRequest);
    }

}
