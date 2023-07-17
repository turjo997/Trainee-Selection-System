package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.ExamMarksRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksHrRequest;
import com.bjit.traineeselectionsystem.model.UploadMarksRequest;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.UploadMarksService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadMarksServiceImpl implements UploadMarksService {

    private final RepositoryManager repositoryManager;


    @Override
    public void uploadMarksByEvaluator(UploadMarksRequest uploadMarksRequest) {
        try {
            // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
            EvaluatorEntity evaluator = repositoryManager.getEvaluatorRepository().findById(uploadMarksRequest.getEvaluatorId())
                    .orElseThrow(() -> new EvaluatorServiceException("Evaluator not found"));

            ApplicantEntity applicant = repositoryManager.getApplicantRepository().findById(uploadMarksRequest.getApplicantId())
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            JobCircularEntity jobCircular = repositoryManager.getJobCircularRepository().findById(uploadMarksRequest.getJobCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Job Circular not found"));

            List<UploadMarksEntity> uploadMarksList = new ArrayList<>();

            // Iterate over the provided exams and marks to create UploadMarksEntities
            for (ExamMarksRequest exam : uploadMarksRequest.getExams()) {
                ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository().findById(exam.getExamId())
                        .orElseThrow(() -> new ExamCreateServiceException("Exam Category not found"));

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
            repositoryManager.getUploadMarksRepository().saveAll(uploadMarksList);
        }catch (EvaluatorServiceException e){
            throw new EvaluatorServiceException(e.getMessage());
        }catch (ApplicantServiceException e){
            throw new ApplicantServiceException(e.getMessage());
        }catch (JobCircularServiceException e){
            throw new JobCircularServiceException(e.getMessage());
        }catch (ExamCreateServiceException e){
            throw new ExamCreateServiceException(e.getMessage());
        }
        catch (Exception e){
            throw e;
        }
    }


    @Override
    public void uploadMarksByAdmin(UploadMarksHrRequest uploadMarksHrRequest ) {
      try {

          // Get the Evaluator, Applicant, Job Circular, and Exam Categories based on the provided IDs
          AdminEntity admin = repositoryManager.getAdminRepository().findById(uploadMarksHrRequest.getAdminId())
                  .orElseThrow(() -> new AdminServiceException("Admin not found"));

          ApplicantEntity applicant = repositoryManager.getApplicantRepository().findById(uploadMarksHrRequest.getApplicantId())
                  .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

          JobCircularEntity jobCircular = repositoryManager.getJobCircularRepository().findById(uploadMarksHrRequest.getJobCircularId())
                  .orElseThrow(() -> new JobCircularServiceException("Job Circular not found"));

          List<UploadMarksHrEntity> uploadMarksList = new ArrayList<>();

          // Iterate over the provided exams and marks to create UploadMarksEntities
          for (ExamMarksRequest exam : uploadMarksHrRequest.getExams()) {
              ExamCategoryEntity examCategory = repositoryManager.getExamCreateRepository().findById(exam.getExamId())
                      .orElseThrow(() -> new ExamCreateServiceException("Exam Category not found"));

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
          repositoryManager.getUploadMarksHrRepository().saveAll(uploadMarksList);

      }catch (AdminServiceException e){
          throw new AdminServiceException(e.getMessage());
      }catch (ApplicantServiceException e){
          throw new ApplicantServiceException(e.getMessage());
      }catch (JobCircularServiceException e){
          throw new JobCircularServiceException(e.getMessage());
      }catch (ExamCreateServiceException e){
          throw new ExamCreateServiceException(e.getMessage());
      }
      catch (Exception e){
          throw e;
      }

    }

}
