package com.bjit.traineeselectionsystem.service;

public interface ApproveService {
    void approveApplicant(Long adminId , Long applicantId , Long circularId , Long examId);

    void selectTopApplicants(Long adminId ,Long circularId , Long examId);
}
