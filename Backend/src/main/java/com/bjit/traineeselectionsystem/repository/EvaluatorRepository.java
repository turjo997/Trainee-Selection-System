package com.bjit.traineeselectionsystem.repository;


import com.bjit.traineeselectionsystem.entity.EvaluatorEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluatorRepository extends JpaRepository<EvaluatorEntity, Long> {
    Optional<EvaluatorEntity> findByUser(UserEntity userEntity);
}
