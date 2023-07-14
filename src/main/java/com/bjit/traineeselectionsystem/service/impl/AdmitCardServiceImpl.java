package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.EntryNotFoundException;
import com.bjit.traineeselectionsystem.model.AdmitCardRequest;
import com.bjit.traineeselectionsystem.model.AdmitCardResponse;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.AdmitCardService;
import com.bjit.traineeselectionsystem.service.AttachmentService;
import com.google.zxing.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmitCardServiceImpl implements AdmitCardService {

    private final AttachmentService attachmentService;
    private final ApplicantRepository applicantRepository;
    private final AdmitCardRepository admitCardRepository;
    private final JobCircularRepository jobCircularRepository;
    private final ExamCreateRepository examCreateRepository;
    private final ApproveRepository approveRepository;
    private final AdminRepository adminRepository;

    @Override
    public void generateAdmitCards(AdmitCardRequest admitCardRequest) {

        AdminEntity adminEntity = adminRepository.findById(admitCardRequest.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));


        JobCircularEntity jobCircularEntity = jobCircularRepository.findById(admitCardRequest.getCircularId())
                .orElseThrow(()-> new IllegalArgumentException("Circular not found"));


        ExamCategoryEntity examCategoryEntity = examCreateRepository.findById(admitCardRequest.getExamId())
                .orElseThrow(()->new IllegalArgumentException("Exam not found"));

        // Get the approved applicants for a specific circular and exam
        List<ApproveEntity> selectedApplicants = approveRepository.findByJobCircularCircularIdAndExamCategoryExamId(admitCardRequest.getCircularId(), admitCardRequest.getExamId());

        List<AdmitCardEntity> addAdmitCard = new ArrayList<>();

        for (ApproveEntity approve : selectedApplicants) {

            AdmitCardEntity admitCardEntity =  AdmitCardEntity.builder()
                    .admin(adminEntity)
                    .applicant(approve.getApplicant())
                    .jobCircular(jobCircularEntity)
                    .instructions(admitCardRequest.getInstructions())
                    .examDate(admitCardRequest.getExamDate())
                    .examTime(admitCardRequest.getExamTime())
                    .examVenue(admitCardRequest.getExamVenue())
                    .build();
            addAdmitCard.add(admitCardEntity);

        }

        // Save the generated AdmitCardEntity to the database or perform any other necessary actions
        admitCardRepository.saveAll(addAdmitCard);
    }

//    @Override
//    public AdmitCardEntity getAdmitCardByApplicantId(Long applicantId) {
//        return (AdmitCardEntity) admitCardRepository.findByApplicantId(applicantId);
//    }


}
