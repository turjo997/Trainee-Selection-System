package com.bjit.traineeselectionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMarksHrRequest {

    private Long adminId;
    private Long applicantId;
    private Long jobCircularId;
    private List<ExamMarksRequest> exams;
}
