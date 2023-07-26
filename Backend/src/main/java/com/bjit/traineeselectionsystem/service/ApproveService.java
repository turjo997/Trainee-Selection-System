package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.model.ApprovalModel;
import org.springframework.http.ResponseEntity;

public interface ApproveService {

    ResponseEntity<String> approveApplicant(ApprovalModel approvalModel);

    ResponseEntity<String> selectTopApplicants(Long userId , Long circularId , Long examId);


    boolean getApproveByApplicantId(Long applicantId, Long circularId);

    boolean isMarksUploadedByApplicantId(Long applicantId, Long circularId);
}
