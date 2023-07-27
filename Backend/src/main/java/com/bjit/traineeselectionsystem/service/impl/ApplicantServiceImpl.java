package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
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

            UserEntity user = repositoryManager.getUserRepository().findById(applyRequest.getUserId())
                    .orElseThrow(() -> new UserServiceException("User not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(user)
                    .orElseThrow(() -> new ApplyServiceException("Applicant not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(applyRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job circular not found"));

            ApplyEntity applyEntity = ApplyEntity.builder()
                    .applicant(applicantEntity)
                    .jobCircular(jobCircularEntity)
                    .build();

            repositoryManager.getApplyRepository().save(applyEntity);

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
            UserEntity user = repositoryManager.getUserRepository().findById(applicantUpdateRequest.getUserId())
                    .orElseThrow(()-> new UserServiceException("User not found"));

            Optional<ApplicantEntity> optionalApplicant = repositoryManager.getApplicantRepository()
                    .findByUser(user);

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
                repositoryManager.getApplicantRepository()
                        .save(applicant);

                return ResponseEntity.ok("applicant updated successfully");

            } else {
                throw new ApplicantServiceException("Applicant not found");
            }
        } catch (UserServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (ApplicantServiceException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAppliedApplicantsByCircularId(Long circularId) {
        System.out.println("circular id  : " + circularId);
        try {
            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository()
                    .findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Job circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository()
                    .findById(1l).orElseThrow(()->new ExamCreateServiceException("Exam not found"));



            List<ApproveEntity> approveEntities = repositoryManager.getApproveRepository()
                    .findByJobCircularAndExamCategory(jobCircularEntity , examCategoryEntity);


            List<ApplyEntity> applyEntities = repositoryManager.getApplyRepository()
                    .findByJobCircular(jobCircularEntity);


            List<ApplicantResponseModel> applicants = new ArrayList<>();

           // if (!approveEntities.isEmpty()) {
                for (ApplyEntity applyEntity : applyEntities) {
                    Long applicantId = applyEntity.getApplicant().getApplicantId();

                    ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository()
                            .findById(applicantId)
                            .orElseThrow(() ->
                                    new ApplyServiceException("Applicant not found with ID: " + applicantId));

                    UserEntity user = repositoryManager.getUserRepository().findById(applicantEntity.getUser().getUserId())
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
          //  }

            return ResponseEntity.ok(applicants);
        } catch (ApplyServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }



    @Override
    public ResponseEntity<?> getApprovedApplicantsForWrittenTest(Long circularId) {
        System.out.println("circular id  : " + circularId);
        try {
            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository()
                    .findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Job circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository()
                    .findById(1l).orElseThrow(()->new ExamCreateServiceException("Exam not found"));



            List<ApproveEntity> approveEntities = repositoryManager.getApproveRepository()
                    .findByJobCircularAndExamCategory(jobCircularEntity , examCategoryEntity);


            List<ApplicantResponseModel> applicants = new ArrayList<>();

            if (!approveEntities.isEmpty()) {
            for (ApproveEntity approveEntity : approveEntities) {
                Long applicantId = approveEntity.getApplicant().getApplicantId();

                ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository()
                        .findById(applicantId)
                        .orElseThrow(() ->
                                new ApplyServiceException("Applicant not found with ID: " + applicantId));

                UserEntity user = repositoryManager.getUserRepository().findById(applicantEntity.getUser().getUserId())
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



    @Override
    public ResponseEntity<?> getApplicantsByTechnicalAndHr(Long circularId , Long examId) {
        System.out.println("circular id  : " + circularId);

        ExamCategoryEntity examCategoryEntity = new ExamCategoryEntity();
        try {
            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository()
                    .findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Job circular not found"));


            if(examId == 3){
                 examCategoryEntity = repositoryManager.getExamCreateRepository()
                        .findById(examId).orElseThrow(()->new ExamCreateServiceException("Exam not found"));
            }

            if(examId == 4){
                 examCategoryEntity = repositoryManager.getExamCreateRepository()
                        .findById(examId).orElseThrow(()->new ExamCreateServiceException("Exam not found"));
            }

            List<ApproveEntity> approveEntities = repositoryManager.getApproveRepository()
                    .findByJobCircularAndExamCategory(jobCircularEntity , examCategoryEntity);


            List<ApplicantResponseModel> applicants = new ArrayList<>();

            // if (!approveEntities.isEmpty()) {
            for (ApproveEntity approveEntity : approveEntities) {
                Long applicantId = approveEntity.getApplicant().getApplicantId();

                ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository()
                        .findById(applicantId)
                        .orElseThrow(() ->
                                new ApplyServiceException("Applicant not found with ID: " + applicantId));

                UserEntity user = repositoryManager.getUserRepository().findById(applicantEntity.getUser().getUserId())
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
            //  }

            return ResponseEntity.ok(applicants);
        } catch (ApplyServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }



    @Override
    public ResponseEntity<?> getApplicantById(Long userId) {
        try {

            UserEntity user = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(() -> new UserServiceException("User not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(user)
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));


            Optional<ApplicantEntity> optionalApplicant = repositoryManager.getApplicantRepository().findById(applicantEntity.getApplicantId());
            if (optionalApplicant.isPresent()) {

                ApplicantCreateModel applicantModel = ApplicantCreateModel.builder()
                        .email(optionalApplicant.get().getUser().getEmail())
                        .firstName(optionalApplicant.get().getFirstName())
                        .lastName(optionalApplicant.get().getLastName())
                        .dob(optionalApplicant.get().getDob())
                        .address(optionalApplicant.get().getAddress())
                        .institute(optionalApplicant.get().getInstitute())
                        .gender(optionalApplicant.get().getGender())
                        .cgpa(optionalApplicant.get().getCgpa())
                        .degreeName(optionalApplicant.get().getDegreeName())
                        .passingYear(optionalApplicant.get().getPassingYear())
                        .contact(optionalApplicant.get().getContact())
                        .build();

                Response<ApplicantCreateModel> apiResponse = new Response<>(applicantModel, null);

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

            } else {
                throw new ApplicantServiceException("applicant not found");
            }
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> getNotificationsForApplicant(Long userId) {

        try {

            UserEntity user = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(() -> new UserServiceException("User not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(user)
                    .orElseThrow(() -> new ApplyServiceException("Applicant not found"));

            List<NotificationEntity> optionalNotice = repositoryManager.getNoticeBoardRepository()
                    .findByApplicant(applicantEntity);

            if (!optionalNotice.isEmpty()) {

                List<NotificationEntity> modelList = new ArrayList<>();
                optionalNotice.forEach(notice -> {
                    modelList.add(
                            NotificationEntity.builder()
                                    .notificationId(notice.getNotificationId())
                                    .title(notice.getTitle())
                                    .description(notice.getDescription())
                                    .build()
                    );
                });

                Response<?> response = new Response<>(modelList, null);
                return ResponseEntity.ok(response);

            } else {
                throw new ApplicantServiceException("applicant not found");
            }
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        }
    }

    @Override
    public boolean isApplied(Long circularId, Long userId) {

        try {
            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            UserEntity userEntity = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(() -> new UserServiceException("User not found"));

            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(userEntity)
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            boolean flag = repositoryManager.getApplyRepository().existsByApplicantAndJobCircular(applicantEntity, jobCircularEntity);
            if (flag) {
                // The user has applied for the job circular
                return flag;
            } else {
                // The user has not applied for the job circular
                return flag;
            }

        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage())).hasBody();
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage())).hasBody();
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage())).hasBody();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(null, e.getMessage())).hasBody();
        }

    }

}
