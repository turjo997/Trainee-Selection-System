package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.ExamMarksRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksByAdminRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksByEvaluatorRequest;
import com.bjit.traineeselectionsystem.service.UploadMarksService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadMarksServiceImpl implements UploadMarksService {

    private final RepositoryManager repositoryManager;


    @Override
    public ResponseEntity<String> uploadMarksByEvaluator(UploadMarksByEvaluatorRequest uploadMarksByEvaluatorRequest) {
        try {
            // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
            EvaluatorEntity evaluator = repositoryManager.getEvaluatorRepository()
                    .findById(uploadMarksByEvaluatorRequest.getEvaluatorId())
                    .orElseThrow(() -> new EvaluatorServiceException("Evaluator not found"));

//            UserEntity user = repositoryManager.getUserRepository()
//                    .findById(uploadMarksRequest.getUserId())
//                    .orElseThrow(() -> new UserServiceException("User not found"));

            JobCircularEntity jobCircular = repositoryManager.getJobCircularRepository()
                    .findById(uploadMarksByEvaluatorRequest.getJobCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job Circular not found"));

            ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                    .findById(uploadMarksByEvaluatorRequest.getApplicantId()).orElseThrow(()
                            -> new ApplicantServiceException("Applicant not found"));


            ApproveEntity approveEntity = repositoryManager.getApproveRepository()
                    .findByApplicantIdAndCircularIdAndCategoryId
                            (applicant.getApplicantId(), uploadMarksByEvaluatorRequest.getJobCircularId(),
                                    uploadMarksByEvaluatorRequest.getExamId()).orElseThrow(()
                            -> new ApproveServiceException("Approval not found"));


            ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository()
                    .findById(uploadMarksByEvaluatorRequest.getExamId()).orElseThrow(()
                            -> new ExamCreateServiceException("Exam not found"));

            // Check if marks already exist for the given Evaluator, Applicant, Job Circular, and Exam Category
            UploadMarksByEvaluatorEntity existingMarks = repositoryManager.getUploadMarksByEvaluatorRepository()
                    .findByApplicantAndJobCircularAndExamCategory(applicant, jobCircular, examCategory);

            if (existingMarks != null) {
                // Marks already exist for the given Evaluator, Applicant, Job Circular, and Exam Category
                // You can choose to return an error response or handle it as per your requirement
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Marks already added for the given circular and exam");
            }


            UploadMarksByEvaluatorEntity uploadMarks = UploadMarksByEvaluatorEntity
                    .builder()
                    .evaluator(evaluator)
                    .applicant(applicant)
                    .jobCircular(jobCircular)
                    .examCategory(examCategory)
                    .marks(uploadMarksByEvaluatorRequest.getMarks())
                    .build();


            // Save the UploadMarksEntities to the repository
            repositoryManager.getUploadMarksByEvaluatorRepository().save(uploadMarks);


            return ResponseEntity.ok("Marks added successfully");

        } catch (EvaluatorServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApproveServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    @Override
    public ResponseEntity<String> uploadMarksByAdmin(UploadMarksByAdminRequest uploadMarksByAdminRequest) {
        try {

            // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
            AdminEntity admin = repositoryManager.getAdminRepository()
                    .findById(uploadMarksByAdminRequest.getAdminId())
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                    .findById(uploadMarksByAdminRequest.getApplicantId())
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            JobCircularEntity jobCircular = repositoryManager.getJobCircularRepository()
                    .findById(uploadMarksByAdminRequest.getJobCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job Circular not found"));

            ApproveEntity approveEntity = repositoryManager.getApproveRepository()
                    .findByApplicantIdAndCircularIdAndCategoryId
                            (applicant.getApplicantId(), uploadMarksByAdminRequest.getJobCircularId(),
                                    uploadMarksByAdminRequest.getExamId()).orElseThrow(()
                            -> new ApproveServiceException("Approval not found"));


            List<UploadMarksByAdminEntity> uploadMarksList = new ArrayList<>();

            ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository()
                    .findById(uploadMarksByAdminRequest.getExamId()).orElseThrow(()
                            -> new ExamCreateServiceException("Exam Category not found"));

            // Check if marks already exist for the given Evaluator, Applicant, Job Circular, and Exam Category
            UploadMarksByAdminEntity existingMarks = repositoryManager.getUploadMarksByAdminRepository()
                    .findByApplicantAndJobCircularAndExamCategory(applicant, jobCircular, examCategory);

            if (existingMarks != null) {
                // Marks already exist for the given Evaluator, Applicant, Job Circular, and Exam Category
                // You can choose to return an error response or handle it as per your requirement
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Marks already added for the given circular and exam");
            }


            UploadMarksByAdminEntity uploadMarks = UploadMarksByAdminEntity
                    .builder()
                    .admin(admin)
                    .applicant(applicant)
                    .jobCircular(jobCircular)
                    .examCategory(examCategory)
                    .marks(uploadMarksByAdminRequest.getMarks())
                    .build();


            // Save the UploadMarksEntities to the repository
            repositoryManager.getUploadMarksByAdminRepository().saveAll(uploadMarksList);
            return ResponseEntity.ok("Marks added successfully");

        } catch (AdminServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (ApproveServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}


//            List<UploadMarksEntity> uploadMarksList = new ArrayList<>();
//
//            // Iterate over the provided exams and marks to create UploadMarksEntities
//            for (ExamMarksRequest exam : uploadMarksRequest.getExams()) {
//
//                ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository().findById(exam.getExamId())
//                        .orElseThrow(() -> new ExamCreateServiceException("Exam Category not found"));
//
//                UploadMarksEntity uploadMarks = UploadMarksEntity.builder()
//                        .evaluator(evaluator)
//                        .applicant(applicant)
//                        .jobCircular(jobCircular)
//                        .examCategory(examCategory)
//                        .marks(exam.getMarks())
//                        .build();
//
//                uploadMarksList.add(uploadMarks);
//            }
//
//            // Save the UploadMarksEntities to the repository
//            repositoryManager.getUploadMarksRepository().saveAll(uploadMarksList);