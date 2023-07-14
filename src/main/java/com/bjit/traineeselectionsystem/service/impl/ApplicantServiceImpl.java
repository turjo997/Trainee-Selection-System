package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ApplyEntity;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import com.bjit.traineeselectionsystem.model.ApplyRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.repository.ApplyRepository;
import com.bjit.traineeselectionsystem.repository.JobCircularRepository;
import com.bjit.traineeselectionsystem.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final JobCircularRepository jobCircularRepository;
    private final ApplyRepository applyRepository;

    @Override
    public ResponseEntity<Response<?>> apply(ApplyRequest applyRequest) {

        // Retrieve the applicant by applicantId from the applyRequest
        ApplicantEntity applicantEntity = applicantRepository.findById(applyRequest.getApplicantId())
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

        // Retrieve the job circular by circularId from the applyRequest
        JobCircularEntity jobCircularEntity = jobCircularRepository.findById(applyRequest.getCircularId())
                .orElseThrow(() -> new IllegalArgumentException("Job circular not found"));

        // Create a new ApplyEntity
        ApplyEntity applyEntity = ApplyEntity.builder()
                .applicant(applicantEntity)
                .jobCircular(jobCircularEntity)
                //.appliedDate(applyRequest.getAppliedDate())
                //.approved(applyRequest.isApproved())
                .build();

        // Save the application to the repository
        ApplyEntity savedApplication = applyRepository.save(applyEntity);

        // Create a response with the saved application
        Response<ApplyEntity> response = new Response<>();
        response.setData(savedApplication);

        return ResponseEntity.ok(response);
    }
}
