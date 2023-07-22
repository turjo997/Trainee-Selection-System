package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ApplyEntity;
import com.bjit.traineeselectionsystem.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeBoardRepository extends JpaRepository<NotificationEntity , Long> {

    Optional<NotificationEntity> findByApplicant(ApplicantEntity applicantEntity);

}
