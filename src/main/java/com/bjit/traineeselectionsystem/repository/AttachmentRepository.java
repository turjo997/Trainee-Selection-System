package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, String> {
}
