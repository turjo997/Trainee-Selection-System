package com.bjit.traineeselectionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorCreateRequest {
    private Long evaluatorId;
    private Long adminId;
    private String evaluatorName;
    private String evaluatorEmail;
    private String designation;
    private String contactNumber;
    private String qualification;
    private String specialization;
    private boolean active;
}