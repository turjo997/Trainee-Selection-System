package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCircularRepository extends JpaRepository<JobCircularEntity , Long> {
    boolean existsByCircularTitle(String circularTitle);
}
