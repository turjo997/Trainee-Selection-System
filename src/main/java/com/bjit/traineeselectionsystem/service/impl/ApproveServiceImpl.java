package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.ApplicantRank;
import com.bjit.traineeselectionsystem.service.ApproveService;
import com.bjit.traineeselectionsystem.utils.ApplicantRankComparator;
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
public class ApproveServiceImpl implements ApproveService {

    private final RepositoryManager repositoryManager;

    @Override
    public ResponseEntity<String> approveApplicant(Long adminId, Long applicantId, Long circularId, Long examId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId(); // 23

            UserEntity userAdmin = repositoryManager.getUserRepository().findById(loggedInApplicantId).orElseThrow(() -> new UserServiceException("User not found"));

            // Applicant --> user(23) -- > email
            AdminEntity admin = repositoryManager.getAdminRepository().findByUser(userAdmin);

            // Check if the applicantId from the ApplyRequest matches the logged-in user's applicantId
            if (adminId != admin.getAdminId()) {
                throw new AdminServiceException("Invalid admin ID");
                // or return an error response indicating that the applicant ID does not match the logged-in user
            }

            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findById(applicantId).orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            Optional<ApplyEntity> applyOptional = repositoryManager.getApplyRepository().findByApplicant(applicantEntity);

            ApplyEntity applyEntity = applyOptional.orElseThrow(() -> new ApplyServiceException("ApplyEntity not found for the given applicant"));
            //.orElseThrow(()-> new IllegalArgumentException("No Application found"));

            // Retrieve the admin entity from the authenticated context
            // Get the admin by adminId from the circularCreateRequest
            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(adminId).orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId).orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(examId).orElseThrow(() -> new ExamCreateServiceException("Exam not found"));

            // Create a new JobCircularEntity
            ApproveEntity approveEntity = ApproveEntity.builder().admin(adminEntity).applicant(applicantEntity).jobCircular(jobCircularEntity).examCategory(examCategoryEntity).approve(true).build();

            // Save the job circular to the repository
            repositoryManager.getApproveRepository().save(approveEntity);

            return ResponseEntity.ok("Applicant approved successfully");

        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApplyServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AdminServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Log the error or handle it as per your application's requirements
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<String> selectTopApplicants(Long adminId, Long circularId, Long examId) {

        try {

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(adminId)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(examId)
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            if (examId == 2) {

                // Fetch all upload marks entries
                List<UploadMarksByEvaluatorEntity> uploadMarksListForWritten =
                        repositoryManager.getUploadMarksByEvaluatorRepository().findAll();

                // Calculate applicant ranks based on marks
                List<ApplicantRank> applicantRanksForWritten = new ArrayList<>();


                if (uploadMarksListForWritten.isEmpty()) {
                    // The list is empty

                    throw new IllegalArgumentException("Written test has not been taken yet");


                } else {
                    // The list is not empty

                    for (UploadMarksByEvaluatorEntity uploadMarks : uploadMarksListForWritten) {
                        ApplicantRank applicantRank = new ApplicantRank();
                        applicantRank.setApplicantId(uploadMarks.getApplicant().getApplicantId());
                        applicantRank.setMarks(uploadMarks.getMarks());
                        applicantRanksForWritten.add(applicantRank);
                    }

                    // Sort applicant ranks in descending order of marks
                    applicantRanksForWritten.sort(new ApplicantRankComparator());


                    // Select top 20 applicants
                    List<ApplicantRank> topApplicantsForWritten = applicantRanksForWritten
                            .subList(0, Math.min(applicantRanksForWritten.size(), 20));

                    // Approve top applicants
                    for (ApplicantRank applicantRank : topApplicantsForWritten) {
                        ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                                .findById(applicantRank.getApplicantId())
                                .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                        ApproveEntity approval = ApproveEntity.builder()
                                .admin(adminEntity)
                                .jobCircular(jobCircularEntity)
                                .examCategory(examCategoryEntity)
                                .applicant(applicant)
                                .approve(true)
                                .build();

                        repositoryManager.getApproveRepository().save(approval);


                    }


                }
                return ResponseEntity.ok("Applicants are approved successfully");

            }

            if (examId == 3) {

                // Approve applicant for exam --- > hr (3)

                // Approve applicant for exam --- > technical (2)
                ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository()
                        .findById(examId).orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


                // Fetch all upload marks entries
                List<UploadMarksByAdminEntity> uploadMarksListForTechnical =
                        repositoryManager.getUploadMarksByAdminRepository()
                                .findByJobCircularCircularIdAndExamCategoryExamId(circularId , 2L);


                if (uploadMarksListForTechnical.isEmpty()) {
                    // The list is empty

                    throw new IllegalArgumentException("Technical test has not been taken yet");

                } else {

                    // Calculate applicant ranks based on marks
                    List<ApplicantRank> applicantRanksForTechnical = new ArrayList<>();

                    for (UploadMarksByAdminEntity uploadMarks : uploadMarksListForTechnical) {
                        ApplicantRank applicantRank = new ApplicantRank();
                        applicantRank.setApplicantId(uploadMarks.getApplicant().getApplicantId());
                        applicantRank.setMarks(uploadMarks.getMarks());
                        applicantRanksForTechnical.add(applicantRank);
                    }

                    // Sort applicant ranks in descending order of marks
                    applicantRanksForTechnical.sort(new ApplicantRankComparator());


                    // Select top 20 applicants
                    List<ApplicantRank> topApplicants = applicantRanksForTechnical.subList(0, Math.min(applicantRanksForTechnical.size(), 20));

                    // Approve top applicants
                    for (ApplicantRank applicantRank : topApplicants) {
                        ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                                .findById(applicantRank.getApplicantId())
                                .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                        ApproveEntity approval = ApproveEntity.builder().admin(adminEntity)
                                .jobCircular(jobCircularEntity).examCategory(examCategory)
                                .applicant(applicant).approve(true).build();

                        repositoryManager.getApproveRepository().save(approval);
                    }

                }

            }
            return ResponseEntity.ok("Applicants are approved successfully");

        } catch (AdminServiceException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
