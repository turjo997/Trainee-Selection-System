package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.AdminServiceException;
import com.bjit.traineeselectionsystem.exception.ExamCreateServiceException;
import com.bjit.traineeselectionsystem.exception.JobCircularServiceException;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.ExamTrackService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import com.bjit.traineeselectionsystem.utils.UniqueCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamTrackServiceImpl implements ExamTrackService {

    private final RepositoryManager repositoryManager;


    @Override
    public ResponseEntity<String> createExamTracks(Long adminId, Long circularId, Long examId) {

        try {

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(adminId)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));


            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));


            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(examId)
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            // Get the approved applicants for a specific circular and exam
            List<ApproveEntity> approvedApplicants = repositoryManager.getApproveRepository().findByJobCircularCircularIdAndExamCategoryExamId(circularId, examId);


            for (ApproveEntity approve : approvedApplicants) {
                String answerSheetCode = UniqueCode.generateUniqueCode();
                ExamTrackEntity examTrack = ExamTrackEntity.builder()
                        .admin(adminEntity)
                        .applicant(approve.getApplicant())
                        .jobCircular(jobCircularEntity)
                        .answerSheetCode(answerSheetCode)
                        .build();

                repositoryManager.getExamTrackRepository().save(examTrack);
            }

            return ResponseEntity.ok("Successfully tracked");

        } catch (AdminServiceException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (JobCircularServiceException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
