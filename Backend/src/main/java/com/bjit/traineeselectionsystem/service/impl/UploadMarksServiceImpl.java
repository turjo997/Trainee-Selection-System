package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.UploadMarksByAdminRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksByEvaluatorRequest;
import com.bjit.traineeselectionsystem.service.UploadMarksService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UploadMarksServiceImpl implements UploadMarksService {

    private final RepositoryManager repositoryManager;


    @Override
    public ResponseEntity<String> uploadMarksByEvaluator(UploadMarksByEvaluatorRequest uploadMarksByEvaluatorRequest) {
        try {

            UserEntity user = repositoryManager.getUserRepository().findById(uploadMarksByEvaluatorRequest.getUserId())
                    .orElseThrow(()-> new UserServiceException("User not found"));



            // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
            EvaluatorEntity evaluator = repositoryManager.getEvaluatorRepository()
                    .findByUser(user)
                    .orElseThrow(() -> new EvaluatorServiceException("Evaluator not found"));

            JobCircularEntity jobCircular = repositoryManager.getJobCircularRepository()
                    .findById(uploadMarksByEvaluatorRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job Circular not found"));

            ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                    .findById(uploadMarksByEvaluatorRequest.getApplicantId()).orElseThrow(()
                            -> new ApplicantServiceException("Applicant not found"));


            ApproveEntity approveEntity = repositoryManager.getApproveRepository()
                    .findByApplicantIdAndCircularIdAndCategoryId
                            (applicant.getApplicantId(), uploadMarksByEvaluatorRequest.getCircularId(),
                                    uploadMarksByEvaluatorRequest.getExamId()).orElseThrow(()
                            -> new ApproveServiceException("Approval not found"));


            ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository()
                    .findById(uploadMarksByEvaluatorRequest.getExamId()).orElseThrow(()
                            -> new ExamCreateServiceException("Exam not found"));

            // Check if marks already exist for the given Evaluator, Applicant, Job Circular, and Exam Category
            UploadMarksByEvaluatorEntity existingMarks = repositoryManager.getUploadMarksByEvaluatorRepository()
                    .findByApplicantAndJobCircularAndExamCategory(applicant, jobCircular, examCategory);

            if (existingMarks != null) {
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

            UserEntity user = repositoryManager.getUserRepository().findById(uploadMarksByAdminRequest.getUserId())
                    .orElseThrow(()-> new UserServiceException("User not found"));

            // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
            AdminEntity admin = repositoryManager.getAdminRepository()
                    .findByUser(user)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                    .findById(uploadMarksByAdminRequest.getApplicantId())
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            JobCircularEntity jobCircular = repositoryManager.getJobCircularRepository()
                    .findById(uploadMarksByAdminRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job Circular not found"));

            ApproveEntity approveEntity = repositoryManager.getApproveRepository()
                    .findByApplicantIdAndCircularIdAndCategoryId
                            (applicant.getApplicantId(), uploadMarksByAdminRequest.getCircularId(),
                                    uploadMarksByAdminRequest.getExamId()).orElseThrow(()
                            -> new ApproveServiceException("Approval not found"));



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
            repositoryManager.getUploadMarksByAdminRepository().save(uploadMarks);
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