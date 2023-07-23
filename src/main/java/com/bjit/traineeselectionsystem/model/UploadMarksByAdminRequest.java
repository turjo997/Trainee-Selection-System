package com.bjit.traineeselectionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMarksByAdminRequest {

    private Long adminId;
    private Long applicantId;
    private Long jobCircularId;
    private Long examId;
    private Double marks;

    //private List<ExamMarksRequest> exams;
}
