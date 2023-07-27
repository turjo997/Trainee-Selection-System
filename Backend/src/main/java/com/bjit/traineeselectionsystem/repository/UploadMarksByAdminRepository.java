package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadMarksByAdminRepository extends JpaRepository<UploadMarksByAdminEntity, Long> {
    List<UploadMarksByAdminEntity> findByJobCircularCircularIdAndExamCategoryExamId
            (Long circularId, Long examCategoryId);

    UploadMarksByAdminEntity findByApplicantAndJobCircularAndExamCategory
            (ApplicantEntity applicantEntity, JobCircularEntity jobCircularEntity
                    , ExamCategoryEntity examCategoryEntity);


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM UploadMarksByAdminEntity a " +
            "WHERE a.applicant.applicantId = :applicantId " +
            "AND a.jobCircular.circularId = :circularId " +
            "AND a.examCategory.examId = :examId ")
    boolean isMarksUploaded(Long applicantId, Long circularId , Long examId);



    List<UploadMarksByAdminEntity> findAllByJobCircularCircularIdAndExamCategoryExamId(Long circularId, Long examId);


}
