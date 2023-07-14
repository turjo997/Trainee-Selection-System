package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdmitCardRepository extends JpaRepository<AdmitCardEntity , Long> {
    AdmitCardEntity findByApplicant(ApplicantEntity applicant);

}
