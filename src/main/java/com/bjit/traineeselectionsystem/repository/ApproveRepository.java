package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApproveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApproveRepository extends JpaRepository<ApproveEntity , Long> {

//    @Query("SELECT ae.applicant FROM ApproveEntity ae WHERE ae.jobCircular.circularId = :circularId AND ae.examCategory.examId = :examId")
//    List<ApplicantEntity> findApplicantsByCircularAndExam(Long circularId, Long examId);
    List<ApproveEntity> findByJobCircularCircularIdAndExamCategoryExamId(Long circularId, Long examId);

    @Query("SELECT a FROM ApproveEntity a " +
            "WHERE a.applicant.applicantId = :applicantId " +
            "AND a.jobCircular.circularId = :circularId " +
            "AND a.examCategory.examId = :categoryId")
    Optional <ApproveEntity> findByApplicantIdAndCircularIdAndCategoryId(@Param("applicantId") Long applicantId,
                                                              @Param("circularId") Long circularId,
                                                              @Param("categoryId") Long categoryId);


//    Optional <ApproveEntity> findByApplicantIdAndJobCircularIdAndExamCategoryId
//            (Long applicantId, Long circularId, Long examId);

}
