package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<ApplyEntity , Long> {

    Optional<ApplyEntity> findByApplicant(ApplicantEntity applicantEntity);

    List<ApplyEntity> findByJobCircular(JobCircularEntity jobCircular);

    boolean existsByApplicantAndJobCircular(ApplicantEntity applicant, JobCircularEntity jobCircular);




}
