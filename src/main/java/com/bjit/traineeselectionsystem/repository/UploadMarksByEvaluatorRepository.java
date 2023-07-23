package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ExamCategoryEntity;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import com.bjit.traineeselectionsystem.entity.UploadMarksByEvaluatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadMarksByEvaluatorRepository extends JpaRepository<UploadMarksByEvaluatorEntity, Long> {

    UploadMarksByEvaluatorEntity findByApplicantAndJobCircularAndExamCategory
            (ApplicantEntity applicantEntity, JobCircularEntity jobCircularEntity
                    , ExamCategoryEntity examCategoryEntity);
}
