package com.bjit.traineeselectionsystem.utils;

import com.bjit.traineeselectionsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryManager {

    private final AdminRepository adminRepository;
    private final AdmitCardRepository admitCardRepository;
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplyRepository applyRepository;
    private final ApproveRepository approveRepository;
    private final AttachmentRepository attachmentRepository;
    private final EvaluatorRepository evaluatorRepository;
    private final ExamCreateRepository examCreateRepository;
    private final ExamTrackRepository examTrackRepository;
    private final JobCircularRepository jobCircularRepository;
    private final MailingStatusRepository mailingStatusRepository;
    private final UploadMarksByAdminRepository uploadMarksByAdminRepository;
    private final UploadMarksByEvaluatorRepository uploadMarksByEvaluatorRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final FinalTraineesRepository finalTraineesRepository;


   public FinalTraineesRepository getFinalTraineesRepository(){
       return finalTraineesRepository;
    }

    public NoticeBoardRepository getNoticeBoardRepository(){
        return noticeBoardRepository;
    }
    public MailingStatusRepository getMailingStatusRepository() {
        return mailingStatusRepository;
    }

    public JobCircularRepository getJobCircularRepository() {
        return jobCircularRepository;
    }

    public ExamCreateRepository getExamCreateRepository() {
        return examCreateRepository;
    }

    public ApproveRepository getApproveRepository() {
        return approveRepository;
    }

    public AdmitCardRepository getAdmitCardRepository(){
        return admitCardRepository;
    }

    public ApplyRepository getApplyRepository(){
        return applyRepository;
    }

    public AttachmentRepository getAttachmentRepository(){
        return attachmentRepository;
    }

    public EvaluatorRepository getEvaluatorRepository(){
        return evaluatorRepository;
    }

    public UploadMarksByAdminRepository getUploadMarksByAdminRepository(){
        return uploadMarksByAdminRepository;
    }

    public ExamTrackRepository getExamTrackRepository(){
        return examTrackRepository;
    }

    public ApplicantRepository getApplicantRepository() {
        return applicantRepository;
    }

    public AdminRepository getAdminRepository() {
        return adminRepository;
    }
    public UploadMarksByEvaluatorRepository getUploadMarksByEvaluatorRepository(){
        return uploadMarksByEvaluatorRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
