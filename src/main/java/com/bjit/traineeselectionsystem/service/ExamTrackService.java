package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.JobCircularEntity;

import java.util.List;

public interface ExamTrackService {
    void createExamTracks(Long adminId, Long circularId, Long examId);
}