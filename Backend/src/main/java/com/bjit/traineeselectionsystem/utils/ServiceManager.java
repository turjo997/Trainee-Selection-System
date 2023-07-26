package com.bjit.traineeselectionsystem.utils;

import com.bjit.traineeselectionsystem.service.*;
import com.bjit.traineeselectionsystem.service.impl.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class ServiceManager {

    private final AdminService adminService;
    private final AdmitCardService admitCardService;
    private final ApplicantService applicantService;
    private final ApproveService approveService;
    private final AttachmentService attachmentService;
    private final CodeGeneratorService codeGeneratorService;
    private final ExamTrackService examTrackService;
    private final MailingStatusService mailingStatusService;
    private final UploadMarksService uploadMarksService;
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final EvaluatorService evaluatorService;


    public EvaluatorService getEvaluatorService(){
        return evaluatorService;
    }

    public EmailSenderService getEmailSenderService() {
        return emailSenderService;
    }
    public AdminService getAdminService(){
        return adminService;
    }

    public AdmitCardService getAdmitCardService(){
        return admitCardService;
    }

    public ApplicantService getApplicantService(){
        return applicantService;
    }

    public ApproveService getApproveService(){
        return approveService;
    }

    public AttachmentService getAttachmentService(){
        return attachmentService;
    }

    public CodeGeneratorService getCodeGeneratorService(){
        return codeGeneratorService;
    }

    public ExamTrackService getExamTrackService(){
        return examTrackService;
    }

    public MailingStatusService mailingStatusService(){
        return mailingStatusService;
    }

    public UploadMarksService getUploadMarksService(){
        return uploadMarksService;
    }
    public UserService getUserService(){
        return userService;
    }


}
