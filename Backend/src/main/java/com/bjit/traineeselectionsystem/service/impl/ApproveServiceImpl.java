package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.ApplicantRank;
import com.bjit.traineeselectionsystem.model.ApprovalModel;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.service.ApproveService;
import com.bjit.traineeselectionsystem.utils.ApplicantRankComparator;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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
    public ResponseEntity<String> approveApplicant(ApprovalModel approvalModel) {

        try {

            Long examId = 1l;

            UserEntity userEntity= repositoryManager.getUserRepository().findById(approvalModel.getUserId())
                    .orElseThrow(()->new UserServiceException("User not found"));

            AdminEntity admin = repositoryManager.getAdminRepository().findByUser(userEntity)
                    .orElseThrow(()-> new AdminServiceException("Admin not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository()
                    .findById(approvalModel.getApplicantId()).orElseThrow(()
                            -> new ApplicantServiceException("Applicant not found"));

            Optional<ApplyEntity> applyOptional = repositoryManager.getApplyRepository()
                    .findByApplicant(applicantEntity);

            applyOptional.orElseThrow(()
                    -> new ApplyServiceException("ApplyEntity not found for the given applicant"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository()
                    .findById(approvalModel.getCircularId()).orElseThrow(()
                            -> new JobCircularServiceException("Circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository()
                    .findById(examId).orElseThrow(() -> new ExamCreateServiceException("Exam not found"));

            // Create a new JobCircularEntity
            ApproveEntity approveEntity = ApproveEntity
                    .builder()
                    .admin(admin)
                    .applicant(applicantEntity)
                    .jobCircular(jobCircularEntity)
                    .examCategory(examCategoryEntity)
                    .approve(true).build();

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
    public ResponseEntity<String> selectTopApplicants(Long userId , Long circularId, Long examId) {

        try {


            UserEntity user = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(()->new UserServiceException("User not found"));


            AdminEntity adminEntity = repositoryManager.getAdminRepository().findByUser(user)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(examId)
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            if (examId == 3) {

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


                        if (repositoryManager.getApproveRepository()
                                .existsByApplicantAndJobCircularAndExamCategory(applicant , jobCircularEntity , examCategoryEntity)) {
                            // Entry already exists, skip creating a new one
                            continue;
                        }

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

            if (examId == 4) {

                // Approve applicant for exam --- > hr (3)

                // Approve applicant for exam --- > technical (2)
                ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository()
                        .findById(examId).orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


                // Fetch all upload marks entries
                List<UploadMarksByAdminEntity> uploadMarksListForTechnical =
                        repositoryManager.getUploadMarksByAdminRepository()
                                .findByJobCircularCircularIdAndExamCategoryExamId(circularId , 3L);


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


                        if (repositoryManager.getApproveRepository()
                                .existsByApplicantAndJobCircularAndExamCategory(applicant , jobCircularEntity , examCategoryEntity)) {
                            // Entry already exists, skip creating a new one
                            continue;
                        }



                        ApproveEntity approval = ApproveEntity.builder().admin(adminEntity)
                                .jobCircular(jobCircularEntity).examCategory(examCategory)
                                .applicant(applicant).approve(true).build();

                        repositoryManager.getApproveRepository().save(approval);
                    }

                }

            }
            return ResponseEntity.ok("Applicants are approved successfully");

        } catch (UserServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AdminServiceException e) {

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



    @Override
    public boolean getApproveByApplicantId(Long applicantId, Long circularId) {
        try {
            Long examId = 1l;


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findById(applicantId)
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            boolean flag = repositoryManager.getApproveRepository()
                    .isApplicantApproved(applicantId, examId,  circularId);

            if (flag) {
                // The user has applied for the job circular
                return flag;
            } else {
                // The user has not applied for the job circular
                return flag;
            }

        }  catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage())).hasBody();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(null, e.getMessage())).hasBody();
        }
    }

    @Override
    public boolean isMarksUploadedByApplicantId(Long applicantId, Long circularId) {
        try {
            Long examId = 1l;

            repositoryManager.getApplicantRepository()
                    .findById(applicantId)
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            boolean flag = repositoryManager.getUploadMarksByEvaluatorRepository()
                    .isMarksUploaded(applicantId,  circularId);


            if (flag) {
                // The user has applied for the job circular
                return flag;
            } else {
                // The user has not applied for the job circular
                return flag;
            }

        }  catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage())).hasBody();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(null, e.getMessage())).hasBody();
        }
    }
}
