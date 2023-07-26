package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ExamCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamCreateRepository extends JpaRepository<ExamCategoryEntity , Long> {
}
