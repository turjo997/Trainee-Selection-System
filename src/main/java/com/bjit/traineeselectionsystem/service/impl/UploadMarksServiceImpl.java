package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.model.ExamMarksRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksHrRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksRequest;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.UploadMarksService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadMarksServiceImpl implements UploadMarksService {
    private final UploadMarksRepository uploadMarksRepository;
    private final UploadMarksHrRepository uploadMarksHrRepository;
    private final EvaluatorRepository evaluatorRepository;
    private final ApplicantRepository applicantRepository;
    private final JobCircularRepository jobCircularRepository;
    private final ExamCreateRepository examCategoryRepository;
    private final AdminRepository adminRepository;

    @Override
    public void uploadMarksByEvaluator(UploadMarksRequest uploadMarksRequest) {
        // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
        EvaluatorEntity evaluator = evaluatorRepository.findById(uploadMarksRequest.getEvaluatorId())
                .orElseThrow(() -> new EntityNotFoundException("Evaluator not found"));

        ApplicantEntity applicant = applicantRepository.findById(uploadMarksRequest.getApplicantId())
                .orElseThrow(() -> new EntityNotFoundException("Applicant not found"));

        JobCircularEntity jobCircular = jobCircularRepository.findById(uploadMarksRequest.getJobCircularId())
                .orElseThrow(() -> new EntityNotFoundException("Job Circular not found"));

        List<UploadMarksEntity> uploadMarksList = new ArrayList<>();

        // Iterate over the provided exams and marks to create UploadMarksEntities
        for (ExamMarksRequest exam : uploadMarksRequest.getExams()) {
            ExamCategoryEntity examCategory = examCategoryRepository.findById(exam.getExamId())
                    .orElseThrow(() -> new EntityNotFoundException("Exam Category not found"));

            UploadMarksEntity uploadMarks = UploadMarksEntity.builder()
                    .evaluator(evaluator)
                    .applicant(applicant)
                    .jobCircular(jobCircular)
                    .examCategory(examCategory)
                    .marks(exam.getMarks())
                    .build();

            uploadMarksList.add(uploadMarks);
        }

        // Save the UploadMarksEntities to the repository
        uploadMarksRepository.saveAll(uploadMarksList);
    }



    @Override
    public void uploadMarksByAdmin(UploadMarksHrRequest uploadMarksHrRequest ) {

        // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
        AdminEntity admin = adminRepository.findById(uploadMarksHrRequest.getAdminId())
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        ApplicantEntity applicant = applicantRepository.findById(uploadMarksHrRequest.getApplicantId())
                .orElseThrow(() -> new EntityNotFoundException("Applicant not found"));

        JobCircularEntity jobCircular = jobCircularRepository.findById(uploadMarksHrRequest.getJobCircularId())
                .orElseThrow(() -> new EntityNotFoundException("Job Circular not found"));

        List<UploadMarksHrEntity> uploadMarksList = new ArrayList<>();

        // Iterate over the provided exams and marks to create UploadMarksEntities
        for (ExamMarksRequest exam : uploadMarksHrRequest.getExams()) {
            ExamCategoryEntity examCategory = examCategoryRepository.findById(exam.getExamId())
                    .orElseThrow(() -> new EntityNotFoundException("Exam Category not found"));

            UploadMarksHrEntity uploadMarks = UploadMarksHrEntity.builder()
                    .admin(admin)
                    .applicant(applicant)
                    .jobCircular(jobCircular)
                    .examCategory(examCategory)
                    .marks(exam.getMarks())
                    .build();

            uploadMarksList.add(uploadMarks);
        }

        // Save the UploadMarksEntities to the repository
        uploadMarksHrRepository.saveAll(uploadMarksList);

    }











}
