package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UploadMarksByEvaluatorRepository extends JpaRepository<UploadMarksByEvaluatorEntity, Long> {


    UploadMarksByEvaluatorEntity findByApplicantAndJobCircularAndExamCategory
            (ApplicantEntity applicantEntity, JobCircularEntity jobCircularEntity
                    , ExamCategoryEntity examCategoryEntity);


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM UploadMarksByEvaluatorEntity a " +
            "WHERE a.applicant.applicantId = :applicantId " +
            "AND a.jobCircular.circularId = :circularId ")
    boolean isMarksUploaded(Long applicantId, Long circularId);

}
