package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApproveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApproveRepository extends JpaRepository<ApproveEntity , Long> {

//    @Query("SELECT ae.applicant FROM ApproveEntity ae WHERE ae.jobCircular.circularId = :circularId AND ae.examCategory.examId = :examId")
//    List<ApplicantEntity> findApplicantsByCircularAndExam(Long circularId, Long examId);
    List<ApproveEntity> findByJobCircularCircularIdAndExamCategoryExamId(Long circularId, Long examId);

    ApproveEntity findByApplicantIdAndJobCircularIdAndExamCategoryId
            (Long applicantId, Long circularId, Long examId);

}
