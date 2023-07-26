package com.bjit.traineeselectionsystem.repository;

import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ExamCategoryEntity;
import com.bjit.traineeselectionsystem.entity.FinalTraineesList;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinalTraineesRepository extends JpaRepository<FinalTraineesList , Long> {

    List<FinalTraineesList> findAllByJobCircularCircularId(Long circularId);

    boolean existsByApplicantAndJobCircular(ApplicantEntity applicant,
                                                           JobCircularEntity jobCircular);
}
