package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.ExamTrackService;
import com.bjit.traineeselectionsystem.utils.UniqueCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamTrackServiceImpl implements ExamTrackService {

    private final ApproveRepository approveRepository;
    private final ExamTrackRepository examTrackRepository;
    private final AdminRepository adminRepository;
    private final JobCircularRepository jobCircularRepository;
    private final ExamCreateRepository examCreateRepository;

    @Override
    public void createExamTracks(Long adminId, Long circularId, Long examId) {

        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));


        JobCircularEntity jobCircularEntity = jobCircularRepository.findById(circularId)
                .orElseThrow(()-> new IllegalArgumentException("Circular not found"));


        ExamCategoryEntity examCategoryEntity = examCreateRepository.findById(examId)
                .orElseThrow(()->new IllegalArgumentException("Exam not found"));


        // Get the approved applicants for a specific circular and exam
        List<ApproveEntity> approvedApplicants = approveRepository.findByJobCircularCircularIdAndExamCategoryExamId(circularId, examId);


        for (ApproveEntity approve : approvedApplicants) {
            String answerSheetCode = UniqueCode.generateUniqueCode();
            ExamTrackEntity examTrack = ExamTrackEntity.builder()
                    .admin(adminEntity)
                    .applicant(approve.getApplicant())
                    .jobCircular(jobCircularEntity)
                    .answerSheetCode(answerSheetCode)
                    .build();

            examTrackRepository.save(examTrack);
        }
    }
}
