package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ApplyEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<ApplyEntity , Long> {

    Optional<ApplyEntity> findByApplicant(ApplicantEntity applicantEntity);
}
