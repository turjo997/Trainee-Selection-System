package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.AdminServiceException;
import com.bjit.traineeselectionsystem.exception.ExamCreateServiceException;
import com.bjit.traineeselectionsystem.exception.JobCircularServiceException;
import com.bjit.traineeselectionsystem.exception.UserServiceException;
import com.bjit.traineeselectionsystem.model.ExamTrackModel;
import com.bjit.traineeselectionsystem.service.ExamTrackService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import com.bjit.traineeselectionsystem.utils.UniqueCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ExamTrackServiceImpl implements ExamTrackService {

    private final RepositoryManager repositoryManager;


    @Override
    public ResponseEntity<String> createExamTracks(ExamTrackModel examTrackModel) {

        try {


            Long examId = 1L;
            UserEntity user = repositoryManager.getUserRepository().findById(examTrackModel.getUserId())
                    .orElseThrow(() -> new UserServiceException("User not found"));
            System.out.println(user);
            AdminEntity adminEntity = repositoryManager.getAdminRepository().findByUser(user)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository()
                    .findById(examTrackModel.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            repositoryManager.getExamCreateRepository()
                    .findById(examId)
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));

            // Get the approved applicants for a specific circular and exam
            List<ApproveEntity> approvedApplicants = repositoryManager.getApproveRepository()
                    .findByJobCircularCircularIdAndExamCategoryExamId(examTrackModel.getCircularId(), examId);

            // Check if an entry already exists for each approved applicant
            for (ApproveEntity approve : approvedApplicants) {
                if (repositoryManager.getExamTrackRepository().existsByApplicantAndJobCircular(
                        approve.getApplicant(), jobCircularEntity)) {
                    // Entry already exists, skip creating a new one
                    continue;
                }

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

        }catch (UserServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (AdminServiceException e) {
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
