package com.bjit.traineeselectionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorModel {
    private Long evaluatorId;
    private Long adminId;
    private Long userId;
    private String evaluatorName;
    private String password;
    private String email;
    private String designation;
    private String contactNumber;
    private String qualification;
    private String specialization;
    private boolean active;
}
