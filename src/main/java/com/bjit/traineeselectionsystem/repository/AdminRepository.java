package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity , Long> {
    Optional<AdminEntity> findByUser(UserEntity userEntity);
}
