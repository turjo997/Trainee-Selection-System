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
    private final UploadMarksHrRepository uploadMarksHrRepository;
    private final UploadMarksRepository uploadMarksRepository;
    private final NoticeBoardRepository noticeBoardRepository;



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

    public UploadMarksHrRepository getUploadMarksHrRepository(){
        return uploadMarksHrRepository;
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
    public UploadMarksRepository getUploadMarksRepository(){
        return uploadMarksRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
