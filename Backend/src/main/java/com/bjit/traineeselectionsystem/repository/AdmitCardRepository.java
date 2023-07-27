package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdmitCardRepository extends JpaRepository<AdmitCardEntity , Long> {
    Optional<AdmitCardEntity> findByApplicant(ApplicantEntity applicant);

}

