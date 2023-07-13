package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.ApproveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ApproveServiceImpl implements ApproveService {

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
}
