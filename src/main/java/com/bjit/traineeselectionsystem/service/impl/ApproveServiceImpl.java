package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.EntryNotFoundException;
import com.bjit.traineeselectionsystem.model.ApplicantRank;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.ApproveService;
import com.bjit.traineeselectionsystem.utils.ApplicantRankComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ApproveServiceImpl implements ApproveService {

    private final UploadMarksRepository uploadMarksRepository;
    private final ApproveRepository approveRepository;
    private final AdminRepository adminRepository;
    private final ApplicantRepository applicantRepository;
    private final ExamCreateRepository examCreateRepository;
    private final JobCircularRepository jobCircularRepository;
    @Override
    public void approveApplicant(Long adminId , Long applicantId , Long circularId , Long examId) {

//        System.out.println(adminId);
//        System.out.println(applicantId);
//        System.out.println(examId);


        // Retrieve the admin entity from the authenticated context
        // Get the admin by adminId from the circularCreateRequest
        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        ApplicantEntity applicantEntity = applicantRepository.findById(applicantId)
                .orElseThrow(()-> new IllegalArgumentException("Applicant not found"));


        JobCircularEntity jobCircularEntity = jobCircularRepository.findById(circularId)
                .orElseThrow(()-> new IllegalArgumentException("Circular not found"));


        ExamCategoryEntity examCategoryEntity = examCreateRepository.findById(examId)
                .orElseThrow(()->new IllegalArgumentException("Exam not found"));



        // Create a new JobCircularEntity
        ApproveEntity approveEntity = ApproveEntity
                .builder()
                .admin(adminEntity)
                .applicant(applicantEntity)
                .jobCircular(jobCircularEntity)
                .examCategory(examCategoryEntity)
                .approve(true)
                .build();

        // Save the job circular to the repository
        approveRepository.save(approveEntity);

    }



    @Override
    public void selectTopApplicants(Long adminId ,Long circularId , Long examId) {

        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));


        JobCircularEntity jobCircularEntity = jobCircularRepository.findById(circularId)
                .orElseThrow(()-> new IllegalArgumentException("Circular not found"));


        ExamCategoryEntity examCategoryEntity = examCreateRepository.findById(examId)
                .orElseThrow(()->new IllegalArgumentException("Exam not found"));


        // Fetch all upload marks entries
        List<UploadMarksEntity> uploadMarksList = uploadMarksRepository.findAll();

        // Calculate applicant ranks based on marks
        List<ApplicantRank> applicantRanks = new ArrayList<>();

        for (UploadMarksEntity uploadMarks : uploadMarksList) {
            ApplicantRank applicantRank = new ApplicantRank();
            applicantRank.setApplicantId(uploadMarks.getApplicant().getApplicantId());
            applicantRank.setMarks(uploadMarks.getMarks());
            applicantRanks.add(applicantRank);
        }

        // Sort applicant ranks in descending order of marks
        applicantRanks.sort(new ApplicantRankComparator());

        // Select top 20 applicants
        List<ApplicantRank> topApplicants = applicantRanks.subList(0, Math.min(applicantRanks.size(), 20));

        // Approve top applicants
        for (ApplicantRank applicantRank : topApplicants) {
            ApplicantEntity applicant = applicantRepository.findById(applicantRank.getApplicantId())
                    .orElseThrow(() -> new EntryNotFoundException("Applicant not found"));

            ApproveEntity approval = ApproveEntity.builder()
                    .admin(adminEntity)
                    .jobCircular(jobCircularEntity)
                    .examCategory(examCategoryEntity)
                    .applicant(applicant)
                    .approve(true)
                    .build();

            approveRepository.save(approval);
        }
    }
}
