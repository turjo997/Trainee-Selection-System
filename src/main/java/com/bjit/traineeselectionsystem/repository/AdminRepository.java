package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity , Long> {
    AdminEntity findByUser(UserEntity userEntity);
}
