package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApproveRepository extends JpaRepository<ApproveEntity , Long> {

    List<ApproveEntity> findByJobCircularCircularIdAndExamCategoryExamId(Long circularId, Long examId);




    @Query("SELECT a FROM ApproveEntity a " +
            "WHERE a.applicant.applicantId = :applicantId " +
            "AND a.jobCircular.circularId = :circularId " +
            "AND a.examCategory.examId = :categoryId")
    Optional <ApproveEntity> findByApplicantIdAndCircularIdAndCategoryId(@Param("applicantId") Long applicantId,
                                                              @Param("circularId") Long circularId,
                                                              @Param("categoryId") Long categoryId);



    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM ApproveEntity a " +
            "WHERE a.applicant.applicantId = :applicantId " +
            "AND a.examCategory.examId = :examId " +
            "AND a.jobCircular.circularId = :circularId " +
            "AND a.approve = true")
    boolean isApplicantApproved(Long applicantId, Long examId, Long circularId);


    List<ApproveEntity> findByJobCircularAndExamCategory
            (JobCircularEntity jobCircular , ExamCategoryEntity examCategoryEntity);


    ApproveEntity findByApplicantAndJobCircularAndExamCategory
            (ApplicantEntity applicantEntity ,
             JobCircularEntity jobCircular , ExamCategoryEntity examCategoryEntity);




    boolean existsByApplicantAndJobCircularAndExamCategory(ApplicantEntity applicant,
                                                           JobCircularEntity jobCircular ,
                                                           ExamCategoryEntity examCategoryEntity);

}
