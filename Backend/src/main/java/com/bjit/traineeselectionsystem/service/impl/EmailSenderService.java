package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.EmailRequest;

import com.bjit.traineeselectionsystem.service.MailingStatusService;

import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import jakarta.mail.MessagingException;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailSenderService implements MailingStatusService {

    private final JavaMailSender mailSender;
    private final RepositoryManager repositoryManager;

    @Override
    public void sendSimpleEmail(EmailRequest emailRequest) {

        try {

            UserEntity user = repositoryManager.getUserRepository().findById(emailRequest.getUserId())
                    .orElseThrow(()-> new UserServiceException("User not found"));

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findByUser(user)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository()
                    .findById(emailRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));


            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository()
                    .findById(emailRequest.getExamId())
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository()
                    .findById(emailRequest.getApplicantId())
                    .orElseThrow(()->new ApplicantServiceException("Applicant not found"));

            // Get the approved applicants for a specific circular and exam
            ApproveEntity approveEntity = repositoryManager.getApproveRepository()
                    .findByApplicantAndJobCircularAndExamCategory
                            (applicantEntity , jobCircularEntity, examCategoryEntity);


            String body = "This is a sample body";
            String subject = "This is a sample subject";


            if(approveEntity.isApprove()){
                UserEntity userEntity = repositoryManager.getUserRepository()
                        .findById(applicantEntity.getUser().getUserId())
                        .orElseThrow(() -> new UserServiceException("User not found"));

                String toEmail = userEntity.getEmail();

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom("97.bhattacharjee.ullash@gmail.com");
                message.setTo(toEmail);
                message.setText(body);
                message.setSubject(subject);


                mailSender.send(message);

                MailingStatusEntity mailingStatusEntity = MailingStatusEntity.builder()
                        .admin(adminEntity)
                        .applicant(applicantEntity)
                        .fromEmail("97.bhattacharjee.ullash@gmail.com")
                        .toEmail(toEmail)
                        .body(body)
                        .subject(subject)
                        .build();

                repositoryManager.getMailingStatusRepository().save(mailingStatusEntity);
            }

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
