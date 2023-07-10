package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity , Long> {
}
