package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.EvaluationEntity;
import com.bjit.traineeselectionsystem.entity.EvaluatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluatorRepository extends JpaRepository<EvaluatorEntity, Long> {
}
