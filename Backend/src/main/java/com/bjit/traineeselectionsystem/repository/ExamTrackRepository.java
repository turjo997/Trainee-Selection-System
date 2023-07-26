package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ExamCategoryEntity;
import com.bjit.traineeselectionsystem.entity.ExamTrackEntity;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamTrackRepository extends JpaRepository<ExamTrackEntity , Long> {

    boolean existsByApplicantAndJobCircular(ApplicantEntity applicant, JobCircularEntity jobCircular);

}
