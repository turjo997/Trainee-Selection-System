package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity , Long> {

    Optional<ApplicantEntity> findByUser(UserEntity userEntity);
}
