package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdmitCardRepository extends JpaRepository<AdmitCardEntity , Long> {
    Optional<AdmitCardEntity> findByApplicant(ApplicantEntity applicant);

    //Optional<Object> findByApplicantApplicantId(Long applicantId);
}

