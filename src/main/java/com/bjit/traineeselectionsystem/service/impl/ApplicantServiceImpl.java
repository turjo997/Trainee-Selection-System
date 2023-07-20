package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ApplyEntity;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import com.bjit.traineeselectionsystem.exception.ApplicantServiceException;
import com.bjit.traineeselectionsystem.exception.ApplyServiceException;
import com.bjit.traineeselectionsystem.exception.JobCircularServiceException;
import com.bjit.traineeselectionsystem.exception.UserServiceException;
import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.service.ApplicantService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final RepositoryManager repositoryManager;

    @Override
    public ResponseEntity<String> apply(ApplyRequest applyRequest) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId();
//
//            UserEntity user = repositoryManager.getUserRepository().findById(loggedInApplicantId)
//                    .orElseThrow(() -> new UserServiceException("User not found"));
//
//            ApplicantEntity applicant = repositoryManager.getApplicantRepository().findByUser(user);
//
//            if (!applyRequest.getApplicantId().equals(applicant.getApplicantId())) {
//                throw new IllegalArgumentException("Invalid applicant ID");
//            }

            UserEntity user = repositoryManager.getUserRepository().findById(applyRequest.getUserId())
                    .orElseThrow(()->new UserServiceException("User not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(user)
                    .orElseThrow(() -> new ApplyServiceException("Applicant not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(applyRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job circular not found"));

            ApplyEntity applyEntity = ApplyEntity.builder()
                    .applicant(applicantEntity)
                    .jobCircular(jobCircularEntity)
                    .build();

            ApplyEntity savedApplication = repositoryManager.getApplyRepository().save(applyEntity);

//            Response<ApplyEntity> response = new Response<>();
//            response.setData(savedApplication);

            return ResponseEntity.ok("Your application has been recorded");
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApplyServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<String> updateApplicant(ApplicantUpdateRequest applicantUpdateRequest) {
        try {
            Optional<ApplicantEntity> optionalApplicant = repositoryManager.getApplicantRepository().findById(applicantUpdateRequest.getApplicantId());

            if (optionalApplicant.isPresent()) {
                ApplicantEntity applicant = optionalApplicant.get();

                // Update the book entity with the new values from the request model
                applicant.setFirstName(applicantUpdateRequest.getFirstName());
                applicant.setLastName(applicantUpdateRequest.getLastName());
                applicant.setAddress(applicantUpdateRequest.getAddress());
                applicant.setDob(applicantUpdateRequest.getDob());
                applicant.setContact(applicantUpdateRequest.getContact());
                applicant.setGender(applicantUpdateRequest.getGender());
                applicant.setInstitute(applicantUpdateRequest.getInstitute());
                applicant.setDegreeName(applicantUpdateRequest.getDegreeName());
                applicant.setCgpa(applicantUpdateRequest.getCgpa());
                applicant.setPassingYear(applicantUpdateRequest.getPassingYear());

                // Save the updated book entity
                ApplicantEntity updatedApplicant = repositoryManager.getApplicantRepository().save(applicant);

                ApplicantUpdateRequest model = ApplicantUpdateRequest.builder()
                        .applicantId(applicantUpdateRequest.getApplicantId())
                        .firstName(applicantUpdateRequest.getFirstName())
                        .lastName(applicantUpdateRequest.getLastName())
                        .dob(applicantUpdateRequest.getDob())
                        .cgpa(applicantUpdateRequest.getCgpa())
                        .address(applicantUpdateRequest.getAddress())
                        .contact(applicantUpdateRequest.getContact())
                        .degreeName(applicantUpdateRequest.getDegreeName())
                        .institute(applicant.getInstitute())
                        .gender(applicantUpdateRequest.getGender())
                        .passingYear(applicantUpdateRequest.getPassingYear())
                        .build();

                //Response<ApplicantUpdateRequest> apiResponse = new Response<>(model, null);
                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok("applicant updated successfully");

            } else {
                throw new ApplicantServiceException("Applicant not found");
            }
        } catch (ApplicantServiceException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAppliedApplicantsByCircularId(Long circularId) {
        System.out.println("circular id  : "+circularId);
        try {
            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Job circular not found"));

            List<ApplyEntity> applyEntities = repositoryManager.getApplyRepository().findByJobCircular(jobCircularEntity);

            List<ApplicantResponseModel> applicants = new ArrayList<>();

            if (!applyEntities.isEmpty()) {
                for (ApplyEntity applyEntity : applyEntities) {
                    Long applicantId = applyEntity.getApplicant().getApplicantId();

                    ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findById(applicantId)
                            .orElseThrow(() -> new ApplyServiceException("Applicant not found with ID: " + applicantId));

                    UserEntity user = repositoryManager.getUserRepository().findById(applicantEntity.getApplicantId())
                            .orElseThrow(() -> new UserServiceException("User not found"));

                    // Create the ApplicantResponseModel from the ApplicantEntity data
                    ApplicantResponseModel applicantResponseModel = new ApplicantResponseModel();
                    // Populate the ApplicantResponseModel fields from the applicantEntity
                    // For example:
                    applicantResponseModel.setApplicantId(applicantEntity.getApplicantId());
                    applicantResponseModel.setFirstName(applicantEntity.getFirstName());
                    applicantResponseModel.setLastName(applicantEntity.getLastName());
                    applicantResponseModel.setAddress(applicantEntity.getAddress());
                    applicantResponseModel.setDob(applicantEntity.getDob());
                    applicantResponseModel.setCgpa(applicantEntity.getCgpa());
                    applicantResponseModel.setEmail(user.getEmail());
                    applicantResponseModel.setContact(applicantEntity.getContact());
                    applicantResponseModel.setDegreeName(applicantEntity.getDegreeName());
                    applicantResponseModel.setGender(applicantEntity.getGender());
                    applicantResponseModel.setPassingYear(applicantEntity.getPassingYear());
                    applicantResponseModel.setInstitute(applicantEntity.getInstitute());
                    // Add other fields as required

                    applicants.add(applicantResponseModel);
                }
            }

            return ResponseEntity.ok(applicants);
        } catch (ApplyServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

}
