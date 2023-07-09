package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.repository.AdminRepository;
import com.bjit.traineeselectionsystem.repository.JobCircularRepository;
import com.bjit.traineeselectionsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final JobCircularRepository jobCircularRepository;

    @Override
    public ResponseEntity<Response<?>> createCircular(CircularCreateRequest circularCreateRequest) {

        // Get the admin by adminId from the circularCreateRequest
        AdminEntity adminEntity = adminRepository.findById(circularCreateRequest.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        // Create a new JobCircularEntity
        JobCircularEntity jobCircularEntity = JobCircularEntity
                .builder()
                .admin(adminEntity)
                .circularTitle(circularCreateRequest.getCircularTitle())
                .jobType(circularCreateRequest.getJobType())
                .openDate(circularCreateRequest.getOpenDate())
                .closeDate(circularCreateRequest.getCloseDate())
                .jobDescription(circularCreateRequest.getJobDescription())
                .status(circularCreateRequest.getStatus())
                .build();

        // Save the job circular to the repository
        JobCircularEntity savedJobCircular = jobCircularRepository.save(jobCircularEntity);

        // Create a response with the saved job circular
        Response<JobCircularEntity> response = new Response<>();
        response.setData(savedJobCircular);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
