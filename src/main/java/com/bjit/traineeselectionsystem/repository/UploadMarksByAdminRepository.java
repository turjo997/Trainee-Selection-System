package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.UploadMarksByAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadMarksByAdminRepository extends JpaRepository<UploadMarksByAdminEntity, Long> {
    List<UploadMarksByAdminEntity> findByJobCircularCircularIdAndExamCategoryExamId
            (Long circularId, Long examCategoryId);
}
